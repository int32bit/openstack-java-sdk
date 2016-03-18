package bupt.openstack.keystone.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.model.User;

public interface UserManager {
	User get(String id) throws OperationException;
	User get() throws OperationException;
	User enabled(String id) throws OperationException;
	User disabled(String id) throws OperationException;
	User changePassword(String id, String password) throws OperationException;
	User changePassword(String password) throws OperationException;
	User updateTenant(String id, String tenantId) throws OperationException;
	User updateTenant(String tenantId) throws OperationException;
	User create(User user) throws OperationException;
	User update(String id, User user) throws OperationException;
	User update(User user) throws OperationException;
	User rename(String id, String name) throws OperationException;
	User rename(String name) throws OperationException;
	void delete(String id) throws OperationException;
	List<User> list() throws OperationException;
	List<User> list(String tenantId) throws OperationException;
}
