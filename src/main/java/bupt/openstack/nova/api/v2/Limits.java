package bupt.openstack.nova.api.v2;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.nova.api.LimitManager;
import bupt.openstack.nova.model.Limit;

public class Limits extends AbstractManager<Limit> implements LimitManager{
	public Limits(Authenticated credentical) {
		super(credentical, Limit.class);
	}

	@Override
	public Limit get() throws OperationException {
		return super._get("/limits", "limits");
	}
}
