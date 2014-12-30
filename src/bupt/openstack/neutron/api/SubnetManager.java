package bupt.openstack.neutron.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.neutron.model.Subnet;

public interface SubnetManager {
	List<Subnet> list() throws OperationException;
	Subnet get(String id) throws OperationException;
	Subnet create(Subnet subnet) throws OperationException;
	Subnet update(Subnet subnet) throws OperationException;
	void delete(String id) throws OperationException;
}
