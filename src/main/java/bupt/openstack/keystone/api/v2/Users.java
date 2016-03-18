package bupt.openstack.keystone.api.v2;
import java.util.List;
import java.util.Objects;

import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.api.*;
import bupt.openstack.keystone.model.*;

public class Users extends AbstractManager<User> implements UserManager {
	private static final String PREFIX = "/users";
	public Users(Authenticated credentical) {
		super(credentical, User.class);
	}
	@Override
	public User get(String id) throws OperationException {
		return super._get("/users/" + id);
	}
	@Override
	public User get() throws OperationException {
		return get(credentical.getUser().getId());
	}
	private User enabled(String id, boolean enabled) throws OperationException {
		JSONObject user = new JSONObject();
		JSONObject property = new JSONObject();
		property.put("id", id);
		property.put("enabled", enabled);
		user.put("user", property);
		return _update(PREFIX + "/" + id + "/OS-KSADM/enabled", user);
	}
	@Override
	public User disabled(String id) throws OperationException {
		return enabled(id, false);
	}
	@Override
	public User enabled(String id) throws OperationException {
		return enabled(id, true);
	}

	@Override
	public User changePassword(String id, String password)
			throws OperationException {
		JSONObject user = new JSONObject();
		JSONObject property = new JSONObject();
		property.put("id", id);
		property.put("enabled", password);
		user.put("user", property);
		return _update(PREFIX + "/" + id + "/OS-KSADM/password", user);
	}
	@Override
	public User changePassword(String password) throws OperationException {
		return changePassword(credentical.getUser().getId(), password);
	}
	@Override
	public User update(String id, User user) throws OperationException {
		Objects.requireNonNull(user);
		user.setId(id);
		return _update(PREFIX + "/" + id, user);
	}
	@Override
	public User update(User user) throws OperationException {
		return update(credentical.getUser().getId(), user);
	}
	@Override
	public User rename(String id, String name) throws OperationException {
		User user = new User();
		user.setName(name);
		return update(id, user);
	}
	@Override
	public User rename(String name) throws OperationException {
		return rename(credentical.getUser().getId(), name);
	}
	@Override
	public User updateTenant(String id, String tenantId)
			throws OperationException {
		User user = new User();
		user.setTenantId(tenantId);
		user.setId(id);
		return _update(PREFIX + "/" + id + "/OS-KSADM/tenant", user);
	}
	@Override
	public User updateTenant(String tenantId) throws OperationException {
		return updateTenant(credentical.getUser().getId(), tenantId);
	}
	@Override
	public User create(User user) throws OperationException {
		Objects.requireNonNull(user);
		Objects.requireNonNull(user.getName());
		return _create(PREFIX, user);
	}

	@Override
	public void delete(String id) throws OperationException {
		super._delete(PREFIX + "/" + id);
	}

	@Override
	public List<User> list() throws OperationException {
		return _list(PREFIX);
	}
	@Override
	public List<User> list(String tenantId) throws OperationException {
		return _list(String.format("/tenants/%s/users", tenantId));
	}
}
