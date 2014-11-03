package bupt.openstack.common.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

import org.apache.logging.log4j.core.config.XMLConfigurationFactory;

import bupt.openstack.common.tools.StringUtils;

public class Configure {
	private Properties property = new Properties();
	private String configruePath;
	private static Configure CONF = null; 
	String[] required = {"AuthURL",
			"DBUrl",
			"DBUsername",
			"DBDriver",
	};
	private Configure() {
		this(System.getenv("OPENSTACK_CONF_PATH"));
	}
	private Configure(String configPath) {
		this(new File(configPath + File.separator + "openstack.conf"));
	}
	private Configure(File configFile) {
		Objects.requireNonNull(configFile);
		// initial property
		initProperty(configFile);
		this.configruePath = configFile.getParent();
		// set logger
		initLogger();
		// checks if all of required fields has been set.
		checkRequired();
	}
	private void initProperty(File file) {
		try(InputStream in = new FileInputStream(file)) {
			try {
				property.load(in);
			} catch (IOException e) {
				throw new ConfigureErrorException(e);
			}
		} catch (IOException e) {
			throw new ConfigureErrorException(e);
		}
	}
	private void initLogger() {
		String loggerPath = getString("LogConfigurePath");
		if (loggerPath == null) {
			loggerPath = this.configruePath + File.separator + "log4j2.xml";
		}
		if (new File(loggerPath).exists()) {
			System.setProperty(XMLConfigurationFactory.CONFIGURATION_FILE_PROPERTY, loggerPath);
			property.put("LogConfigurePath", loggerPath);
		}
	}
	public String getString(String key) {
		Objects.requireNonNull(key, "Key is null");
		return property.getProperty(key);
	}
	public int getInt(String key) {
		Objects.requireNonNull(key);
		return Integer.parseInt(property.getProperty(key));
	}
	public Object get(String key) {
		Objects.requireNonNull(key);
		return property.get(key);
	}
	private void checkRequired() {
		for (String r : this.required) {
			if (StringUtils.isBlank(getString(r))) {
				throw new ConfigureErrorException("Failed to configure environment, '%s' required!", r);
			}
		}
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Entry<?, ?> entry : property.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			sb.append(key + " = " + value + '\n');
		}
		return sb.toString();
	}
	public static Configure getConfigure() {
		if (CONF == null) {
			synchronized(Configure.class) {
				if (CONF == null) {
					CONF = new Configure();
				}
			}
		}
		return CONF;
	}
	public static Configure getConfigure(String configPath) {
		if (CONF == null) {
			synchronized(Configure.class) {
				if (CONF == null) {
					CONF = new Configure(configPath);
				}
			}
		}
		return CONF;
	}
	public static Configure getConfigure(File configFile) {
		if (CONF == null) {
			synchronized(Configure.class) {
				if (CONF == null) {
					CONF = new Configure(configFile);
				}
			}
		}
		return CONF;
	}
	public static void main(String[] args) {
		System.out.println(Configure.getConfigure());
	}
}
