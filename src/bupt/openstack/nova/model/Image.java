package bupt.openstack.nova.model;

import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.model.Link;
@Entity("image")
public class Image extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Property
	private String status;
	@Property
	private String updated;
	@Property
	private List<Link> links;
	@Property
	private String created;
	@Property
	private int minDisk;
	@Property
	private int progress;
	@Property
	private int minRam;
	@Property
	private JSONObject metadata;
	@Property("OS-EXT-IMG-SIZE:size")
	private int size;
	public Image() {
		super();
		
	}

	public Image(JSONObject entity) {
		super(entity);
		
	}

	public Image(Object obj) {
		super(obj);
		
	}

	public Image(String s) {
		super(s);
		
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

	public List<Link> getLinks() {
		return links;
	}

	public String getCreated() {
		return created;
	}

	public int getMinDisk() {
		return minDisk;
	}

	public int getProgress() {
		return progress;
	}

	public int getMinRam() {
		return minRam;
	}

	public JSONObject getMetadata() {
		return metadata;
	}

	public int getSize() {
		return size;
	}
}
