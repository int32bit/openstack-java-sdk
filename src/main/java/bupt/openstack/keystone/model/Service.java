package bupt.openstack.keystone.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("OS-KSADM:service")
public class Service extends AbstractEntity {
	private static final long serialVersionUID = -7193593739250648503L;
	@Property
	private String description;
	@Property
	private String type;
	public Service() {
		super();
	}
	public Service(JSONObject service) {
		super(service);
	}

	public Service(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Service(String s) {
		this(new JSONObject(s));
	}
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean isValid() {
		return true;
	}
}