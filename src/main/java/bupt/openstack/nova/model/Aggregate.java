package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("aggregate")
public class Aggregate extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1897765793153803288L;
	@Property("availability_zone")
	private String availabilityZone;
	@Property("created_at")
	private String createdAt;
	@Property
	private boolean deleted;
	@Property("updated_at")
	private String updatedAt;
	@Property("delete_at")
	private String deleteAt;
	@Property
	private JSONObject metadata;
	public Aggregate() {
		super();
	}
	public Aggregate(JSONObject json) {
		super(json);
		if (json.has("id")) {
			int id = json.getInt("id");
			this.id = String.valueOf(id);
		}
	}
	public Aggregate(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Aggregate(String s) {
		this(new JSONObject(s));
	}
	@Override
	public boolean isValid() {
		return true;
	}
	public String getAvailabilityZone() {
		return availabilityZone;
	}
	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}
	public JSONObject getMetadata() {
		return metadata;
	}
	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public String getDeleteAt() {
		return deleteAt;
	}
	
}
