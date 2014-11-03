package bupt.openstack.keystone.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.api.RoleManager;
import bupt.openstack.keystone.api.TenantManager;
import bupt.openstack.keystone.api.UserManager;
import bupt.openstack.keystone.model.Tenant;
import bupt.openstack.keystone.model.User;

public class Tenants extends AbstractManager<Tenant> implements TenantManager {
	private UserManager users;
	private RoleManager roles;
	private static final String PREFIX = "/tenants";
	public Tenants(Authenticated credentical) {
		super(credentical, Tenant.class);
		this.users = new Users(credentical);
		this.roles = new Roles(credentical);
	}

	@Override
	public List<Tenant> list() throws OperationException {
		return super._list(PREFIX);
	}

	@Override
	public Tenant get(String id) throws OperationException {
		return super._get(PREFIX + "/" + id);
	}

	@Override
	public Tenant create(Tenant tenant) throws OperationException {
		return super._create(PREFIX, tenant);
	}

	@Override
	public Tenant create(String name, String description)
			throws OperationException {
		Tenant tenant = new Tenant();
		tenant.setName(name);
		tenant.setDescription(description);
		return create(tenant);
	}

	@Override
	public Tenant update(String id, Tenant tenant) throws OperationException {
		tenant.setId(id);
		return super._update(PREFIX + "/" + id, tenant);
	}

	@Override
	public Tenant rename(String id, String name) throws OperationException {
		Tenant tenant = new Tenant();
		tenant.setName(name);
		return update(id, tenant);
	}

	@Override
	public Tenant enabled(String id) throws OperationException {
		Tenant tenant = new Tenant();
		tenant.setEnabled(true);
		return update(id, tenant);
	}

	@Override
	public Tenant disabled(String id) throws OperationException {
		Tenant tenant = new Tenant();
		tenant.setEnabled(false);
		return update(id, tenant);
	}

	@Override
	public List<User> listUsers(String id) throws OperationException {
		return users.list(id);
	}

	@Override
	public void addUser(String tenantId, String userId, String roleId)
			throws OperationException {
		roles.addUserRole(roleId, userId, tenantId);
	}

	@Override
	public void removeUser(String tenantId, String userId, String roleId)
			throws OperationException {
		roles.removeUserRole(roleId, userId, tenantId);
	}
	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);
	}

}
