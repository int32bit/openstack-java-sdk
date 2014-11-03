package bupt.openstack.keystone.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.model.Role;

public interface RoleManager {
	/**
	 * Get a Role
	 * @param id The id of role to get.
	 * @return The Role you want.
	 * @throws OperationException
	 */
	Role get(String id) throws OperationException;
	/**
	 * List all available roles.
	 * @return The List of the role.
	 * @throws OperationException
	 */
	List<Role> list() throws OperationException;
	/**
	 * Get all roles for a user globally.
	 * @param userId The id of user.
	 * @return The List of the roles.
	 * @throws OperationException
	 */
	List<Role> list(String userId, String TenantId) throws OperationException;
	/**
	 * Adds a role to a user, the role is added just for that tenant.
	 * @param roleId The id of role that you want to add into.
	 * @param userId The id of user you want to add.
	 * @param tenantId The id of tenantId you want to add into.
	 * @return The updated role.
	 * @throws OperationException
	 */
	Role addUserRole(String roleId, String userId, String tenantId) throws OperationException;
	/**
	 * Adds a role to a user, the role is added globally.
	 * @param roleId The id of role you want to add into.
	 * @param userId The id of user you want to add.
	 * @return The updated role.
	 * @throws OperationException
	 */
	void removeUserRole(String roleId, String userId, String tenantId) throws OperationException;
	/**
	 * Removes a role from a user, the role is removed from the user's global roles.
	 * @param roleId The roleId you want to remove from.
	 * @param userId The id of user you want to remove.
	 * @throws OperationException
	 */
	Role create(String name) throws OperationException;
	/**
	 * Delete a Role.
	 * @param id The id of role to delete.
	 * @throws OperationException
	 */
	void delete(String id) throws OperationException;
	
}
