package bupt.openstack.nova.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.model.Link;
@Entity("Version")
public class Version extends AbstractEntity {
	/**
	 * 
	 */
	private List<Link> links;
	private static final long serialVersionUID = 1L;
	@Property
	private String status;
	@Property
	private String updated;
	public Version(JSONObject version) {
		super(version);
	}
	public Version(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Version(String s) {
		this(new JSONObject(s));
	}
	@Override
	public boolean isValid() {
		return true;
	}
	public String getStatus() {
		return status;
	}
	public String getUpdated() {
		return updated;
	}
	public List<Link> getlinks() {
		return Collections.unmodifiableList(links);
	}
}
