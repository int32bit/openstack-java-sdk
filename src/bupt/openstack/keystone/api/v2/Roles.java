package bupt.openstack.keystone.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.api.RoleManager;
import bupt.openstack.keystone.model.Role;

public class Roles extends AbstractManager<Role> implements RoleManager {
	private static final String PREFIX = "/OS-KSADM/roles";
	public Roles(Authenticated credentical) {
		super(credentical, Role.class);
	}

	@Override
	public Role get(String id) throws OperationException {
		return super._get(PREFIX + "/" + id);
	}

	@Override
	public List<Role> list() throws OperationException {
		return super._list(PREFIX);
	}

	@Override
	public List<Role> list(String userId, String tenantId)
			throws OperationException {
		String route;
		if (tenantId != null) {
			route = String.format("/tenants/%s/users/%s/roles", tenantId, userId);
		} else {
			route = String.format("/users/%s/roles", userId);
		}
		return super._list(route);
	}
	@Override
	public Role addUserRole(String roleId, String userId, String tenantId)
			throws OperationException {
		String route;
		if (tenantId != null) {
			route = String.format("/tenants/%s/users/%s/roles/OS-KSADM/%s", tenantId, userId, roleId);
		} else {
			route = String.format("/users/%s/roles/OS-KSADM/%s", userId, roleId);
		}
		return super._update(route, null);
	}

	@Override
	public void removeUserRole(String roleId, String userId, String tenantId)
			throws OperationException {
		String route;
		if (tenantId != null) {
			route = String.format("/tenants/%s/users/%s/roles/OS-KSADM/%s", tenantId, userId, roleId);
		} else {
			route = String.format("/users/%s/roles/OS-KSADM/%s", userId, roleId);
		}
		super._delete(route);
	}

	@Override
	public Role create(String name) throws OperationException {
		Role role = new Role();
		role.setName(name);
		return super._create(PREFIX, role);
	}
	/**
	 * FIXME 无论成功还是失败，都会抛出异常
	 */
	@Override
	public void delete(String id) throws OperationException {
		try {
			super._delete(PREFIX + "/" + id);
		} catch (Exception e) {
			
		}
	}

}
