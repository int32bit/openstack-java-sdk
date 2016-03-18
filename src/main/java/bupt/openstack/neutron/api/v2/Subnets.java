package bupt.openstack.neutron.api.v2;

import java.util.List;
import java.util.Objects;

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
	@Override
	public Subnet create(Subnet subnet) throws OperationException {
		return super._create(PREFIX + "/subnets.json", subnet);
	}
	@Override
	public Subnet update(Subnet subnet) throws OperationException {
		Objects.requireNonNull(subnet);
		Objects.requireNonNull(subnet.getId());
		String id = subnet.getId();
		// To ignore read-only attribute  
		subnet.setCIDR(null);
		subnet.setId(null);
		subnet.setNetworkId(null);
		subnet.setTenantId(null);
		return super._update(PREFIX + "/subnets/" + id + ".json", subnet);
	}
	@Override
	public void delete(String id) throws OperationException {
		super._delete(PREFIX + "/subnets/" + id + ".json");
	}
}
