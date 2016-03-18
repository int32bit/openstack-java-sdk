package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Flavor;

public interface FlavorManager{
	List <Flavor> list() throws OperationException;
	Flavor get(String id)throws OperationException;
	Flavor update(Flavor entity) throws OperationException;
	Flavor create(Flavor entity) throws OperationException;
	void delete(String id) throws OperationException;
}
