package bupt.openstack.nova.api;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Limit;

public interface LimitManager {
	Limit get() throws OperationException;
}
