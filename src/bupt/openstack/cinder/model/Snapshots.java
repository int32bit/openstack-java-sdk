package bupt.openstack.cinder.model;

import java.util.List;

import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.cinder.api.v1.AbstractManager;
import bupt.openstack.cinder.api.v1.SnapshotManager;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.request.HttpMethod;
import bupt.openstack.common.request.Response;

public class Snapshots extends AbstractManager<Snapshot> implements
		SnapshotManager {
	private static final String PREFIX = "/snapshots";
	public Snapshots(Authenticated credentical) {
		super(credentical, Snapshot.class);
	}

	@Override
	public List<Snapshot> list() throws OperationException {
		return _list(PREFIX + "/detail");
	}

	@Override
	public Snapshot create(String volumeId, String name, String description)
			throws OperationException {
		Snapshot snapshot = new Snapshot();
		snapshot.setVolumeId(volumeId);
		snapshot.setName(name);
		snapshot.setDescription(description);
		return _create(PREFIX, snapshot);
	}

	@Override
	public Snapshot get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}

	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);
	}

	@Override
	public Snapshot update(String id, String name, String description)
			throws OperationException {
		Snapshot shot = new Snapshot();
		shot.setId(id);
		shot.setName(name);
		shot.setDescription(description);
		return _update(PREFIX + "/" + id, shot);
	}

	@Override
	public Snapshot rename(String id, String name) throws OperationException {
		return update(id, name, null);
	}

	@Override
	public Snapshot updateDescription(String id, String description)
			throws OperationException {
		return update(id, null, description);
	}

	@Override
	public void resetState(String id, String state) throws OperationException {
		JSONObject info = new JSONObject();
		JSONObject body = new JSONObject();
		info.put("status", state);
		body.put("os-reset_status", info);
		Response response = null;
		try {
			response = super.request(PREFIX + "/" + id + "/action", HttpMethod.POST, body);
		} catch (Exception e) {
			throw new OperationException(e);
		}
		if (!response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
	}

	@Override
	public void setMetadata(String id, String key, String value)
			throws OperationException {
		JSONObject body = new JSONObject();
		JSONObject data = new JSONObject();
		data.put(key, value);
		body.put("metadata", data);
		Response response = null;
		try {
			response = super.request(PREFIX + "/" + id + "/metadata", HttpMethod.POST, body);
		} catch (Exception e) {
			throw new OperationException(e);
		}
		if (!response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
	}

	@Override
	public void deleteMetadata(String id, String key) throws OperationException {
		_delete(PREFIX + "/" + id + "/metadata/" + id);
	}

	@Override
	public void updateMetadata(String id, String key, String value)
			throws OperationException {
		setMetadata(id, key, value);
	}
}
