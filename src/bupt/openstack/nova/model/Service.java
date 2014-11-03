package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("service")
public class Service extends AbstractEntity {
	private static final long serialVersionUID = 5661514699603067262L;
	@Property
	private String status;
	@Property
	private String binary;
	@Property
	private String zone;
	@Property
	private String state;
	@Property("udpated_at")
	private String updatedAt;
	@Property
	private String host;
	@Property("disabled_reason")
	private String disabledReason;

	public Service() {
	}

	public Service(JSONObject service) {
		super(service);
		name = binary;
		id = name + "@" +  host;
	}
	public Service(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Service(String s) {
		this(new JSONObject(s));
	}
	public String getStatus() {
		return status;
	}

	public String getBinary() {
		return binary;
	}

	public String getZone() {
		return zone;
	}

	public String getState() {
		return state;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public String getHost() {
		return host;
	}

	public String getDisabledReason() {
		return disabledReason;
	}

	@Override
	public boolean isValid() {
		return true;
	}
}