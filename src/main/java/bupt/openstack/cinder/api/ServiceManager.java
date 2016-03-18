package bupt.openstack.cinder.api;

import java.util.List;

import bupt.openstack.cinder.model.Service;
import bupt.openstack.common.OperationException;

public interface ServiceManager {
	List<Service> list() throws OperationException;
	Service enable(String host, String name) throws OperationException;
	Service disalbe(String host, String name) throws OperationException;
}
