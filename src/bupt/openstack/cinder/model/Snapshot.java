package bupt.openstack.cinder.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("snapshot")
public class Snapshot extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6629164568875251623L;
	@Property("volume_id")
	private String volumeId;
	@Property("display_description")
	private String description;
	@Property("display_name")
	private String name;
	@Property
	private boolean force;
	@Property
	private String status;
	@Property("create_at")
	private String createAt;
	@Property("os-extended-snapshot-attributes:project_id")
	private String tenantId;
	@Property
	private int size;
	@Property
	private JSONObject metadata;
	@Property("os-extended-snapshot-attributes:progress")
	private String progress;
	public Snapshot() {
		this.force = false;
	}
	public Snapshot(JSONObject json) {
		super(json);
	}
	public Snapshot(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Snapshot(String s) {
		this(new JSONObject(s));
	}
	@Override
	public boolean isValid() {
		return true;
	}
	public String getVolumeId() {
		return volumeId;
	}
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	public boolean isForce() {
		return force;
	}
	public void setVolumeId(String volumeId) {
		this.volumeId = volumeId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setForce(boolean force) {
		this.force = force;
	}
	public String getStatus() {
		return status;
	}
	public String getCreateAt() {
		return createAt;
	}
	public String getTenantId() {
		return tenantId;
	}
	public int getSize() {
		return size;
	}
	public JSONObject getMetadata() {
		return metadata;
	}
	public String getProgress() {
		return progress;
	}
	
}
