package bupt.openstack.neutron;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.Client;
import bupt.openstack.neutron.api.NetworkManager;
import bupt.openstack.neutron.api.SubnetManager;
import bupt.openstack.neutron.api.v2.Networks;
import bupt.openstack.neutron.api.v2.Subnets;

public class Neutron extends Client {
	public final NetworkManager networks;
	public final SubnetManager subnets;
	public Neutron(Authenticated credentical) {
		super(credentical);
		this.networks = new Networks(credentical);
		this.subnets = new Subnets(credentical);
	}
}
