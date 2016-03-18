package bupt.openstack.cinder.api;

import bupt.openstack.cinder.model.Limit;
import bupt.openstack.common.OperationException;

public interface LimitManager {
	 Limit get() throws OperationException;
}
