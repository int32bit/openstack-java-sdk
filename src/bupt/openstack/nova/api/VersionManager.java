package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Version;

public interface VersionManager {
	List<Version> list() throws OperationException;
}
