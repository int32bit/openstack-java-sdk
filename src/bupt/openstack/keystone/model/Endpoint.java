package bupt.openstack.keystone.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("endpoint")
public class Endpoint extends AbstractEntity {
	private static final long serialVersionUID = -7301936545753023431L;
	@Property("adminurl")
	private String adminURL;
	@Property("publicurl")
	private String publicURL;
	@Property("internalurl")
	private String internalURL;
	@Property("region")
	private String region;
	@Property("type")
	private String type;
	@Property("service_id")
	private String serviceId;
	@Property
	private boolean enabled;

	public Endpoint() {
		super();
		this.enabled = true;
	}

	public Endpoint(JSONObject endpoint) {
		super(endpoint);
	}
	public Endpoint(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Endpoint(String s) {
		this(new JSONObject(s));
	}
	public String getAdminURL() {
		return adminURL;
	}

	public String getPublicURL() {
		return publicURL;
	}

	public String getInternalURL() {
		return internalURL;
	}

	public String getRegion() {
		return region;
	}

	public String getType() {
		return type;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setAdminURL(String adminURL) {
		this.adminURL = adminURL;
	}

	public void setPublicURL(String publicURL) {
		this.publicURL = publicURL;
	}

	public void setInternalURL(String internalURL) {
		this.internalURL = internalURL;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isValid() {
		return true;
	}
}