package bupt.openstack.neutron.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.neutron.api.SubnetManager;
import bupt.openstack.neutron.model.Subnet;

public class Subnets extends AbstractManager<Subnet> implements SubnetManager {
	private static final String PREFIX = "/v2.0";
	public Subnets(Authenticated credentical) {
		super(credentical, Subnet.class);
	}

	@Override
	public List<Subnet> list() throws OperationException {
		return super._list(PREFIX + "/subnets.json");
	}
	@Override
	public Subnet get(String id) throws OperationException {
		return super._get(PREFIX + "/subnets/" + id + ".json");
	}

}
