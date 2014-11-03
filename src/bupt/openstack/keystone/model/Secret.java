package bupt.openstack.keystone.model;

import org.json.JSONObject;

import bupt.openstack.common.tools.StringUtils;

public class Secret {
	private String username;
	private String password;
	private String tenantId;
	private String tenantName;
	private String token;
	public Secret() {
		super();
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isValid() {
		boolean hasCredencial = false;
		boolean hasTenant = false;
		if (StringUtils.isBlank(token)) {
			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				hasCredencial = true;
			} else {
				hasCredencial = false;
			}
		} else {
			hasCredencial = true;
		}
		hasTenant = StringUtils.isNotBlank(tenantId) || StringUtils.isNotBlank(tenantName);
		return hasCredencial && hasTenant;
	}
	@Override
	public String toString() {
		JSONObject secret = new JSONObject();
		JSONObject auth = new JSONObject();
		if (StringUtils.isNotBlank(token)) {
			JSONObject property = new JSONObject();
			property.put("id", token);
			auth.put("token", property);
		} else {
			JSONObject property = new JSONObject();
			property.put("username", username);
			property.put("password", password);
			auth.put("passwordCredentials", property);
		}
		if (tenantId != null) {
			auth.put("tenantId", tenantId);
		} else {
			auth.put("tenantName", tenantName);
		}
		secret.put("auth", auth);
		return secret.toString();
	}
}
