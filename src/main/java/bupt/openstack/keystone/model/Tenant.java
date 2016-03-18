package bupt.openstack.keystone.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class Tenant extends AbstractEntity {
	private static final long serialVersionUID = -6909170837159431317L;
	@Property
	private String description;
	@Property
	private boolean enabled;
	public Tenant() {
		super();
		this.enabled = true;
	}

	public Tenant(JSONObject entity) {
		super(entity);
	}

	public Tenant(Object obj) {
		super(obj);
	}

	public Tenant(String s) {
		super(s);
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isValid() {
		return true;
	}
}