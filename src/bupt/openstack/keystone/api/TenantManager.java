package bupt.openstack.keystone.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.model.Tenant;
import bupt.openstack.keystone.model.User;

public interface TenantManager {
	List<Tenant> list() throws OperationException;
	Tenant get(String id) throws OperationException;
	Tenant create(Tenant tenant) throws OperationException;
	Tenant create(String name, String description) throws OperationException;
	Tenant update(String id, Tenant tenant) throws OperationException;
	Tenant rename(String id, String name) throws OperationException;
	Tenant enabled(String id) throws OperationException;
	Tenant disabled(String id) throws OperationException;
	List<User> listUsers(String id) throws OperationException;
	void  addUser(String tenantId, String userId, String roleId) throws OperationException;
	void removeUser(String tenantId, String userId, String roleId) throws OperationException;
	void delete(String id) throws OperationException;
}
