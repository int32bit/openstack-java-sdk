package bupt.openstack.nova;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.nova.api.AggregateManager;
import bupt.openstack.nova.api.FlavorManager;
import bupt.openstack.nova.api.HypervisorManager;
import bupt.openstack.nova.api.KeyPairManager;
import bupt.openstack.nova.api.LimitManager;
import bupt.openstack.nova.api.QuotaManager;
import bupt.openstack.nova.api.ServerManager;
import bupt.openstack.nova.api.ServiceManager;
import bupt.openstack.nova.api.VersionManager;
import bupt.openstack.nova.api.v2.Aggregates;
import bupt.openstack.nova.api.v2.Flavors;
import bupt.openstack.nova.api.v2.Hypervisors;
import bupt.openstack.nova.api.v2.KeyPairs;
import bupt.openstack.nova.api.v2.Limits;
import bupt.openstack.nova.api.v2.Quotas;
import bupt.openstack.nova.api.v2.SecurityGroupManager;
import bupt.openstack.nova.api.v2.SecurityGroups;
import bupt.openstack.nova.api.v2.Servers;
import bupt.openstack.nova.api.v2.Services;
import bupt.openstack.nova.api.v2.Versions;

public class Nova {
	public final FlavorManager flavors;
	public final HypervisorManager hypervisors;
	public final ServiceManager services;
	public final ServerManager servers;
	public final KeyPairManager keypairs;
	public final VersionManager versions;
	public final LimitManager limits;
	public final QuotaManager quotas;
	public final AggregateManager aggregates;
	public final SecurityGroupManager securityGroups;
	private Authenticated credentical;
	public Nova(Authenticated credentical) {
		this.credentical = credentical;
		flavors = new Flavors(credentical);
		hypervisors = new Hypervisors(credentical);
		services = new Services(credentical);
		servers = new Servers(credentical);
		keypairs = new KeyPairs(credentical);
		versions = new Versions(credentical);
		limits = new Limits(credentical);
		quotas = new Quotas(credentical);
		aggregates = new Aggregates(credentical);
		securityGroups = new SecurityGroups(credentical);
	}
	public void setRegion(String region) {
		credentical.setWorkRegion(region);
	}
}
