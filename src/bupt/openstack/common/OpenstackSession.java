package bupt.openstack.common;

import java.util.List;
import java.util.Set;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.cinder.Cinder;
import bupt.openstack.common.conf.Configure;
import bupt.openstack.common.tools.DataBaseAccessor;
import bupt.openstack.glance.Glance;
import bupt.openstack.keystone.Keystone;
import bupt.openstack.keystone.model.Secret;
import bupt.openstack.neutron.Neutron;
import bupt.openstack.nova.Nova;

public class OpenstackSession {
	/**
	 * 该用户所属的tenant集合
	 */
	private List<String> availableTenants;
	private Authenticated credentical;
	private final Nova nova;
	private final Glance glance;
	private final Keystone keystone;
	private final Cinder cinder;
	private final Neutron neutron;
	@SuppressWarnings("unused")
	private final Configure configure;
	/**
	 * 获取一个session实例，需要传递证书作为参数，第一次认证时不建议直接调用该方法，建议直接调用静态方法getSession().
	 * @param credentical 证书
	 */
	public OpenstackSession(Authenticated credentical) {
		configure = Configure.getConfigure();
		this.credentical = credentical;
		this.nova = new Nova(credentical);
		this.glance = new Glance(credentical);
		this.keystone = new Keystone(credentical);
		this.cinder = new Cinder(credentical);
		this.neutron = new Neutron(credentical);
		this.availableTenants = DataBaseAccessor.getTenants(credentical.getUser().getName());
	}
	/**
	 * 返回一个使用nova实例
	 * @return Nova
	 */
	public Nova getNovaClient() {
		return nova;
	}
	public Glance getGlanceClient() {
		return glance;
	}
	public Keystone getKeystoneClient() {
		return keystone;
	}
	public Cinder getCinderClient() {
		return cinder;
	}
	public Neutron getNeutronClient() {
		return neutron;
	}
	/**
	 * 切换region
	 * @param region 需要切换代region，region必须存在，否则失败
	 */
	public void switchRegionTo(String region) {
		credentical.setWorkRegion(region);
	}
	/**
	 * 获取所有可用代region集合
	 * @return region集合
	 */
	public Set<String> getAvailableRegions() {
		return credentical.getRegions();
	}
	/**
	 * 获取当前工作region
	 * @return 当前工作region名
	 */
	public String getCurrentWorkRegion() {
		return credentical.getWorkRegion();
	}
	/**
	 * 销毁该session实例同时会销毁与之绑定的证书
	 * @throws OperationException
	 */
	public void destroy() throws OperationException {
		keystone.tokens.destroy(credentical.getTokenId());
	}
	/**
	 * 返回与该session绑定的证书
	 * @return
	 */
	public Authenticated getCredentical() {
		return credentical;
	}
	public List<String> getAvailableTenants() {
		return this.availableTenants;
	}
	/**
	 * 通过用户名和密码获取session实例，默认tenant由配置文件指定，如果获取tenant失败，将抛出异常
	 * @param username 用户名
	 * @param password 密码
	 * @return 该帐户的session实例
	 * @throws OperationException
	 * @throws TenantNotFoundException 
	 */
	public static OpenstackSession getSession(String username, String password) throws OperationException, TenantNotFoundException {
		Secret secret = new Secret();
		secret.setUsername(username);
		secret.setPassword(password);
		String value = Configure.getConfigure().getString("TenantName");
		if (value == null) { 
			value = Configure.getConfigure().getString("TenantId");
			/* 
			 * tenantId 也没有设置，则直接从数据库中获取一个随机tenant
			 */
			if (value == null) {
				List<String> tenants = DataBaseAccessor.getTenants(username);
				if (tenants.size() < 1) {
					throw new TenantNotFoundException("Failed to get a valid tenant for " + username);
				} else {
					secret.setTenantName(tenants.get(0));
				}
			} else {
				secret.setTenantId(value.toString());
			}
		} else {
			secret.setTenantName(value.toString());
		}
		assert(secret.isValid());
		Authenticated credentical = Keystone.authenticate(secret);
		return new OpenstackSession(credentical);
	}
	/**
	 * 通过用户名和密码，获取指定tenant的session实例
	 * @param tenantName tenant名，比如admin
	 * @param username 用户名
	 * @param password 密码
	 * @return session实例
	 * @throws OperationException
	 */
	public static OpenstackSession getSession(String tenantName, String username, String password) throws OperationException  {
		Secret secret = new Secret();
		secret.setPassword(password);
		secret.setTenantName(tenantName);
		secret.setUsername(username);
		/** 必须保证secret合法 **/
		assert(secret.isValid());
		return new OpenstackSession(Keystone.authenticate(secret));
	}
	/**
	 * 使用token获取session，token必须和配置文件的一致
	 * @param token token
	 * @return session实例
	 * @throws OperationException
	 * @throws TenantNotFoundException 
	 */
	public static OpenstackSession getSession(String token) throws OperationException, TenantNotFoundException {
		Secret secret = new Secret();
		secret.setToken(token);
		String value = Configure.getConfigure().getString("TenantName");
		/**
		 * 先尝试获取tenant名称，获取失败则获取tenant的id，如果都失败了，抛出异常
		 */
		if (value == null) { 
			value = Configure.getConfigure().getString("TenantId");
			/* 
			 * tenantId 也没有设置，则直接从数据库中获取一个随机tenant
			 */
			if (value == null) {
				List<String> tenants = DataBaseAccessor.getTenants("admin");
				if (tenants.size() < 1) {
					throw new TenantNotFoundException("Failed to get a valid tenant for admin.");
				} else {
					secret.setTenantName(tenants.get(0));
				}
			} else {
				secret.setTenantId(value.toString());
			}
		} else {
			secret.setTenantName(value.toString());
		}
		assert(secret.isValid());
		return new OpenstackSession(Keystone.authenticate(secret));
	}
}
