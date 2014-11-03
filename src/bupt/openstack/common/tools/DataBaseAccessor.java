package bupt.openstack.common.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.openstack.common.conf.Configure;

public class DataBaseAccessor {
	private static Logger logger = LogManager.getFormatterLogger(DataBaseAccessor.class);
	private static byte[] lock = new byte[0];
	private static DataBaseAccessor dao = null;
	protected final String username;
	protected final String password;
	protected final String driver;
	protected final String url;
	private DataSource dataSource = null;
	private DataBaseAccessor() {
		super();
		Configure conf = Configure.getConfigure();
		username = (String) conf.getString("DBUsername");
		password = (String) conf.getString("DBPassword");
		driver = (String) conf.getString("DBDriver");
		url = (String) conf.getString("DBUrl");
		String format = "OpenstackDao initial: username: %s password: %s url : %s driver : %s";
		logger.debug(format, username, password, url, driver);
		setup();
	}

	private void setup() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				url, username, password);
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, null);
		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(
				poolableConnectionFactory);
		poolableConnectionFactory.setPool(connectionPool);
		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(
				connectionPool);
		this.dataSource = dataSource;
		logger.debug("get DataSource");
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public static DataBaseAccessor getInstance() {
		if (dao == null) {
			synchronized (lock) {
				if (dao == null) {
					dao = new DataBaseAccessor();
				}
			}
		}
		return dao;
	}
	public static List<String> getTenants(String username) {
		DataBaseAccessor dao = DataBaseAccessor.getInstance();
		List<String> tenants = new ArrayList<>();
		String sql = "select project.name from keystone.project as project where project.id in ";
		sql += "(select assignment.target_id from keystone.assignment as assignment";
		sql += " inner join keystone.user as user on assignment.actor_id = user.id where user.name='%s');";
		logger.debug("sql = %s", sql);
		try (   Connection conn = dao.getDataSource().getConnection();
				Statement state = conn.createStatement();
				ResultSet rs = state.executeQuery(String.format(sql, username))
			) {
			while (rs.next()) {
				String name = rs.getString("name");
				tenants.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tenants;
	}
}
