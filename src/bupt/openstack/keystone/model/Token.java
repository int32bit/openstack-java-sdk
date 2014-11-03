package bupt.openstack.keystone.model;

import java.util.Date;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.tools.DateConverter;
@Entity("token")
public class Token extends AbstractEntity {
	private static final long serialVersionUID = -733311016900606838L;
	@Property
	private Date expires;
	@Property("issued_at")
	private Date issuedAt;
	@Property
	private Tenant tenant;
	public Token(JSONObject json) {
		this.issuedAt = DateConverter.parse(json.getString("issued_at"));
		this.expires = DateConverter.parse(json.getString("expires"));
		this.tenant = new Tenant(json.getJSONObject("tenant"));
		this.id = json.getString("id");
		this.name = tenant.getName();
	}
	public Date getExpires() {
		return this.expires;
	}
	public boolean isExpired() {
		if (expires == null)
			return true;
		return new Date().getTime() >= expires.getTime();
	}

	public Date getIssuedAt() {
		return this.issuedAt;
	}
	public Tenant getTenant() {
		return this.tenant;
	}
	@Override
	public boolean isValid() {
		return !isExpired();
	}
}
