package bupt.openstack.nova.api.v2;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.tools.StringUtils;
import bupt.openstack.nova.api.QuotaManager;
import bupt.openstack.nova.model.Quota;

public class Quotas extends AbstractManager<Quota> implements QuotaManager {
	private static final String PREFIX = "//os-quota-sets";
	public Quotas(Authenticated credentical) {
		super(credentical, Quota.class);
	}
	@Override
	public Quota getDefault() throws OperationException {
		String url = PREFIX + "/" + credentical.getTenant().getId() + "/defaults";
		return super._get(url);
	}
	private String format(String tenantName, String username) {
		String url = PREFIX + "/" + tenantName;
		if (StringUtils.isNotBlank(username)) {
			url += "?user_id=" + username;
		}
		return url;
	}
	@Override
	public Quota get(String tenantName, String username) throws OperationException {
		String url = format(tenantName, username);
		return _get(url);
	}

	@Override
	public void delete(String tenantName, String username)
			throws OperationException {
		String url = format(tenantName, username);
		_delete(url);
	}

	@Override
	public Quota update(String tenantName, String username, Quota quota) throws OperationException {
		quota.setTenantId(tenantName);
		String url = format(tenantName, username);
		return super._update(url, quota);
	}

	@Override
	public Quota create(String tenantName, String username, Quota quota) throws OperationException {
		return update(tenantName, username, quota);
	}
	@Override
	public Quota get(String tenantName) throws OperationException {
		return get(tenantName, null);
	}
	@Override
	public void delete(String tenantName) throws OperationException {
		delete(tenantName, null);
		
	}
	@Override
	public Quota update(String tenantName, Quota quota) throws OperationException {
		return update(tenantName, null, quota);
	}
	@Override
	public Quota update(Quota quota) throws OperationException {
		return update(credentical.getTenant().getName(), credentical.getUser().getName(), quota);
	}
	@Override
	public Quota create(String tenantId, Quota quota) throws OperationException {
		return create(tenantId, null, quota);
	}
	
}
