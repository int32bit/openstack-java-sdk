package bupt.openstack.authentication;

import java.util.Set;

import bupt.openstack.keystone.model.CatalogType;
import bupt.openstack.keystone.model.Tenant;
import bupt.openstack.keystone.model.User;

public interface Authenticated {
	String getTokenId();
	User getUser();
	Tenant getTenant();
	String getEndpoint(CatalogType type);
	String getWorkRegion();
	void setWorkRegion(String region);
	Set<String> getRegions();
}
