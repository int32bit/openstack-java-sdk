package bupt.openstack.neutron.api.v2;
import java.util.List;
import java.util.Objects;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.neutron.api.NetworkManager;
import bupt.openstack.neutron.model.*;
public class Networks extends AbstractManager<Network> implements NetworkManager{
	private static final String PREFIX = "/v2.0";
	public Networks(Authenticated credentical) {
		super(credentical, Network.class);
	}
	@Override
	public List<Network> list() throws OperationException {
		return super._list(PREFIX + "/networks.json");
	}
	@Override
	public Network create(Network network) throws OperationException {
		return super._create(PREFIX + "/networks.json", network.toJSONObject(false));
	}
	@Override
	public void delete(String id) throws OperationException {
		super._delete(PREFIX + "/networks/" + id + ".json");
	}
	@Override
	public Network get(String id) throws OperationException {
		return super._get(PREFIX + "/networks/" + id + ".json");
	}
	@Override
	public List<Network> listExternal() throws OperationException {
		String url = PREFIX + "/networks.json?router%3Aexternal=True";
		return _list(url);
	}
	@Override
	public Network update(Network network) throws OperationException {
		Objects.requireNonNull(network);
		Objects.requireNonNull(network.getId());
		String id = network.getId();
		Network t = new Network();
		t.setName(network.getName());
		t.setExternal(network.isExternal());
		t.setShared(network.isShared());
		t.setUp(network.isUp());
		return super._update(PREFIX + "/networks/" + id + ".json", t.toJSONObject(false));
	}
	
}
