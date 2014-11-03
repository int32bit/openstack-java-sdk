package bupt.openstack.cinder.api;

import java.util.List;
import java.util.Map;

import bupt.openstack.cinder.model.VolumeType;
import bupt.openstack.common.OperationException;

public interface VolumeTypeManager {
	List<VolumeType> list() throws OperationException;
	VolumeType get(String id) throws OperationException;
	void delete(String id) throws OperationException;
	VolumeType create(String name) throws OperationException;
	void setKey(String id, String key, String value) throws OperationException;
	Map<String, String> getKeys(String id) throws OperationException;
	void unsetKey(String id, String key) throws OperationException;
}
