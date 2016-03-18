package bupt.openstack.cinder.api.v1;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.cinder.api.LimitManager;
import bupt.openstack.cinder.model.Limit;
import bupt.openstack.common.OperationException;

public class Limits extends AbstractManager<Limit> implements LimitManager {

	public Limits(Authenticated credentical) {
		super(credentical, Limit.class);
	}

	@Override
	public Limit get() throws OperationException {
		return _get("/limits");
	}

}
