package bupt.openstack.nova.api;

import bupt.openstack.common.OperationException;
import bupt.openstack.common.model.AbstractEntity;

public interface ReadWriteManager<T extends AbstractEntity> extends ReadOnlyManager<T> {
	T create(T entity) throws OperationException;
	void update(T entity) throws OperationException;
	void delete(String id) throws OperationException;
}
