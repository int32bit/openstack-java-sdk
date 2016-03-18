package bupt.openstack.keystone.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.model.Endpoint;

public interface EndpointManager {
	List<Endpoint> list() throws OperationException;
	Endpoint create(Endpoint endpoint) throws OperationException;
	Endpoint create(String region, String serviceId, String publicurl, String adminurl, String internalurl) throws OperationException;
	void delete(String id) throws OperationException;
}
