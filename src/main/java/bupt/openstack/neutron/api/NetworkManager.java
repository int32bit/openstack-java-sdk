package bupt.openstack.neutron.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.neutron.model.Network;

public interface NetworkManager {
	List<Network> list() throws OperationException;
	List<Network> listExternal() throws OperationException;
	Network create(Network network) throws OperationException;
	void delete(String id) throws OperationException;
	Network get(String id) throws OperationException;
	Network update(Network network) throws OperationException;
}
