package bupt.openstack.keystone.model;

import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.tools.DataBaseAccessor;

public class PasswordCredential {
	private String username;
	private String password;
	private String tenantName;
	public PasswordCredential(String tenantName, String username, String password) {
		this.tenantName = tenantName;
		this.username = username;
		this.password = password;
	}

	public PasswordCredential(String username, String password) {
		this(null, username, password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getTenantName() {
		return this.tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public JSONObject toJSONObject() {
		JSONObject token = new JSONObject();
		JSONObject passwordCredentials = null;
		JSONObject t = new JSONObject();
		if (this.username.length() > 0) {
			passwordCredentials = new JSONObject();
			passwordCredentials.put("username", this.username);
			passwordCredentials.put("password", this.password);
		}
		if (tenantName != null) {
			t.put("tenantName", tenantName);
		}
		if (tenantName == null) {
			List<String> tenantNames = DataBaseAccessor.getTenants(username);
			if (tenantNames.size() > 0) {
				t.put("tenantName", tenantNames.get(0));
			}
		}
		if (passwordCredentials != null)
			t.put("passwordCredentials", passwordCredentials);
		else {
			t.put("passwordCredentials", "");
		}
		token.put("auth", t);
		return token;
	}

	@Override
	public String toString() {
		return toJSONObject().toString();
	}
}