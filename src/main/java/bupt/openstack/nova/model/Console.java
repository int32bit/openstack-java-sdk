package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;

@Entity("console")
public class Console extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -399886903935656981L;
	@Property
	private String url;
	@Property
	private String type;
	public Console(JSONObject json) {
		super(json);
	}
	public Console(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	@Override
	public boolean isValid() {
		return url != null;
	}
	public String getUrl() {
		return url;
	}
	public String getType() {
		return type;
	}
	
}
