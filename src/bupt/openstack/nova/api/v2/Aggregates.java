package bupt.openstack.nova.api.v2;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.tools.JSONConverter;
import bupt.openstack.common.tools.StringUtils;
import bupt.openstack.nova.api.AggregateManager;
import bupt.openstack.nova.model.Aggregate;

public class Aggregates extends AbstractManager<Aggregate> implements AggregateManager {
	private static final String PREFIX = "/os-aggregates";
	public Aggregates(Authenticated credentical) {
		super(credentical, Aggregate.class);
	}
	@Override
	public List<Aggregate> list() throws OperationException {
		return _list(PREFIX);
	}

	@Override
	public Aggregate get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}

	@Override
	public Aggregate create(String name, String zone) throws OperationException {
		JSONObject aggregate = new JSONObject();
		JSONObject property = new JSONObject();
		Objects.requireNonNull(name);
		property.put("name", name);
		property.put("availability_zone", zone);
		aggregate.put("aggregate", property);
		return _create(PREFIX, aggregate);
	}
	/*@Override
	public Aggregate update(Aggregate aggregate)
			throws OperationException {
		Objects.requireNonNull(aggregate);
		String id = aggregate.getId();
		Objects.requireNonNull(id);
		String name = aggregate.getName();
		String zone = aggregate.getAvailabilityZone();
		JSONObject _aggregate = new JSONObject();
		JSONObject property = new JSONObject();
		//property.put("name", name);
		property.put("availability_zone", zone);
		_aggregate.put("aggregate", property);
		return _update(PREFIX + "/" + aggregate.getId(), _aggregate);
	}*/
	@Override
	public Aggregate update(String id, String name, String zone) throws OperationException {
		JSONObject aggregate = new JSONObject();
		JSONObject property = new JSONObject();
		if (StringUtils.isNotBlank(name)) {
			property.put("name", name);
		}
		if (StringUtils.isNotBlank(zone)) {
			property.put("availability_zone", zone);
		}
		aggregate.put("aggregate", property);
		return _update(PREFIX + "/" + id, aggregate);
	}
	@Override
	public Aggregate updateAvailableZone(String id, String zone) throws OperationException {
		return update(id, null, zone);
	}
	@Override
	public Aggregate rename(String id, String name) throws OperationException {
		return update(id, name, null);
	}
	private Aggregate doAction(String id, String cmd, JSONObject options) throws OperationException {
		JSONObject body = new JSONObject();
		body.put(cmd, options);
		return _create(PREFIX + "/" + id + "/action", body);
		
	}
	@Override
	public Aggregate addHost(String id, String host) throws OperationException {
		JSONObject options = new JSONObject();
		options.put("host", host);
		return doAction(id, "add_host", options);
	}

	@Override
	public Aggregate removeHost(String id, String host)
			throws OperationException {
		JSONObject options = new JSONObject();
		options.put("host", host);
		return doAction(id, "remove_host", options);
	}

	@Override
	public Aggregate setMetaData(String id, Map<String, ?> metadata)
			throws OperationException {
		JSONObject data = JSONConverter.mapToJSON(metadata);
		JSONObject options = new JSONObject();
		options.put("metadata", data);
		return doAction(id, "set_metadata", options);
	}

	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);

	}

}
