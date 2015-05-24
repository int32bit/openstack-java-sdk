package bupt.openstack.keystone.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class User extends AbstractEntity {
	private static final long serialVersionUID = 1497888419304968741L;
	@Property
	private String email;
	@Property
	private String password;
	@Property
	private boolean enabled;
	@Property
	private String username;
	private List<String> tenantNames = new ArrayList<>();
	@Property
	private String tenantId;
	public User() {
		super();
		enabled = true;
	}
	public User(JSONObject user) {
		super(user);
		if (username == null) {
			username = name;
		}
	}
	public User(Object o) {
		this(new JSONObject(o.toString()));
	}
	public User(String s) {
		this(new JSONObject(s));
	}
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getTenantNemes() {
		return Collections.unmodifiableList(tenantNames);
	}

	public void setTenantNames(List<String> ids) {
		tenantNames.addAll(ids);
	}
	public String getUsername() {
		return username;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantId() {
		return this.tenantId;
	}
	@Override
	public boolean isValid() {
		return true;
	}
}