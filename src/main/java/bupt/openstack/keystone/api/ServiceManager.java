package bupt.openstack.keystone.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.model.Service;

public interface ServiceManager {
	List<Service> list() throws OperationException;
	Service get(String id) throws OperationException;
	Service create(String name, String type, String description) throws OperationException;
	Service create(Service service) throws OperationException;
	void delete(String id) throws OperationException;
}
