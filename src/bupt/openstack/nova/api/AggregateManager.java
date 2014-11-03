package bupt.openstack.nova.api;
import java.util.List;
import java.util.Map;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Aggregate;

public interface AggregateManager {
	List<Aggregate> list() throws OperationException;
	Aggregate get(String id) throws OperationException;
	Aggregate create(String name, String zone) throws OperationException;
	Aggregate rename(String id, String name) throws OperationException;
	Aggregate updateAvailableZone(String id, String zone) throws OperationException;
	Aggregate update(String id, String name, String zone) throws OperationException;
	Aggregate addHost(String id, String host) throws OperationException;
	Aggregate removeHost(String id, String host) throws OperationException;
	Aggregate setMetaData(String id, Map<String, ?> metadata) throws OperationException;
	void delete(String id) throws OperationException;
}
