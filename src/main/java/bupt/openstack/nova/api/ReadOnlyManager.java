package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.common.model.AbstractEntity;

public interface ReadOnlyManager<T extends AbstractEntity> extends BaseManager{
	T get(String id) throws OperationException;
	List<T> list() throws OperationException;
}
