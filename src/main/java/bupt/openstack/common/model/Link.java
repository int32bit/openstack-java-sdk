package bupt.openstack.common.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;

@Entity("link")
public class Link extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2932302662917021170L;
	@Property
	private String href;
	@Property
	private String rel;
	@Property
	private String type;
	public Link() {
		super();
	}
	public Link(JSONObject link) {
		super(link);
	}
	public Link(Object o) {
		super(o);
	}
	public Link(String s) {
		super(s);
	}
	public Link(String href, String rel) {
		this.href = href;
		this.rel = rel;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getRel() {
		return this.rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
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
