package bupt.openstack.common.request;

import java.util.Set;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.authentication.AuthenticatedRequest;
import bupt.openstack.keystone.model.CatalogType;
import bupt.openstack.keystone.model.Tenant;
import bupt.openstack.keystone.model.User;

public class OpenstackHTTPClient  extends HTTPRequest implements AuthenticatedRequest{
	protected Authenticated session = null;
	public OpenstackHTTPClient(Authenticated session) {
		this.session = session;
	}
	@Override
	public String getTokenId() {
		return session.getTokenId();
	}
	@Override
	public User getUser() {
		return session.getUser();
	}
	@Override
	public Tenant getTenant() {
		return session.getTenant();
	}
	@Override
	public void before() {
		clean();
		setHeader("Content-Type", "application/json");
		setHeader("Accept", "application/json");
		if (getTenant() != null) {
			setHeader("X-Auth-Project-Id", getTenant().getName());
			setHeader("X-Auth-Token", getTokenId());
		}
	}
	@Override 
	public void before(Header header) {
		clean();
		setHeader(header);
	}
	@Override
	public void close() throws Exception {
		super.close();
		if (session instanceof AutoCloseable) {
			((AutoCloseable) session).close();
		}
	}
	@Override
	public String getEndpoint(CatalogType type) {
		return session.getEndpoint(type);
	}
	@Override
	public String getWorkRegion() {
		return session.getWorkRegion();
	}
	@Override
	public void setWorkRegion(String region) {
		session.setWorkRegion(region);
	}
	@Override
	public Set<String> getRegions() {
		return session.getRegions();
	}
}
