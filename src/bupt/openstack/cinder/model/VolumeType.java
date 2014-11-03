package bupt.openstack.cinder.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("volume_type")
public class VolumeType extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6539238104579991330L;
	@Property("extra_specs")
	private JSONObject metadata;
	public VolumeType() {
		super();
	}
	public VolumeType(JSONObject json) {
		super(json);
	}
	public VolumeType(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public VolumeType(String s) {
		this(new JSONObject(s));
	}
	@Override
	public boolean isValid() {
		return true;
	}
	public JSONObject getMetadata() {
		return metadata;
	}
}
