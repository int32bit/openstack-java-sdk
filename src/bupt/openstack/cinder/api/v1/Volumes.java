package bupt.openstack.cinder.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.cinder.api.VolumeManager;
import bupt.openstack.cinder.model.MountMode;
import bupt.openstack.cinder.model.Snapshot;
import bupt.openstack.cinder.model.Snapshots;
import bupt.openstack.cinder.model.Volume;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.request.HttpMethod;
import bupt.openstack.common.request.Response;
import bupt.openstack.common.tools.StringUtils;

public class Volumes extends AbstractManager<Volume> implements VolumeManager {
	private static final String PREFIX = "/volumes";
	private SnapshotManager snapshots;
	public Volumes(Authenticated credentical) {
		super(credentical, Volume.class);
		snapshots = new Snapshots(credentical);
	}
	private void  doAction(String id, String action, Map<String, ? extends Object>info) throws OperationException {
		JSONObject body = new JSONObject();
		if (info == null) {
			body.put(action, JSONObject.NULL);
		} else {
			body.put(action, new JSONObject(info));
		}
		String url = String.format(PREFIX + "/%s/action", id);
		Response response = null;
		try {
			response = super.request(url, HttpMethod.POST, body);
		} catch(Exception e) {
			throw new OperationException(e);
		}
		if (!response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
	}
	private void doAction(String id, String action) throws OperationException {
		doAction(id, action, null);
	}
	@Override
	public List<Volume> list() throws OperationException {
		return super._list(PREFIX + "/detail");
	}

	@Override
	public Volume get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}

	@Override
	public Volume create(String name, int size, String description)
			throws OperationException {
		Volume volume = new Volume();
		volume.setDescription(description);
		volume.setName(name);
		volume.setSize(size);
		return create(volume);
	}

	@Override
	public Volume create(Volume volume) throws OperationException {
		return _create(PREFIX, volume);
	}

	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);
	}

	@Override
	public Volume rename(String id, String name) throws OperationException {
		return update(id, name, null);
	}

	@Override
	public Volume update(String id, String name, String desc)
			throws OperationException {
		if (StringUtils.isBlank(name) && StringUtils.isBlank(desc))
			return get(id);
		Volume volume = new Volume();
		if (StringUtils.isNotBlank(name)) {
			volume.setName(name);
		}
		if (StringUtils.isNotBlank(desc)) {
			volume.setDescription(desc);
		}
		return _update(PREFIX + "/" + id, volume);
	}

	@Override
	public Volume updateDescription(String id, String desc)
			throws OperationException {
		return update(id, null, desc);
	}
	@Override
	public void mount(String devId, String serverId, String mountpoint,
			MountMode mode) throws OperationException {
		Map<String, String> info = new HashMap<>(3);
		info.put("instance_uuid", serverId);
		info.put("mountpoint", mountpoint);
		info.put("mode", mode.toString());
		doAction(devId, "os-attach", info);
	}
	@Override
	public void mount(String devId, String serverId) throws OperationException {
		mount(devId, serverId, "auto", MountMode.rw);
	}
	@Override
	public void umount(String devId) throws OperationException {
		doAction(devId, "os-detach");
	}
	@Override
	public void resize(String id, int size) throws OperationException {
		Map<String, String> info = new HashMap<>(1);
		info.put("new_size", String.valueOf(size));
		doAction(id, "os-extend", info);
	}
	@Override
	public void resetState(String id, String state) throws OperationException {
		Map<String, String> info = new HashMap<>(1);
		info.put("status", state);
		doAction(id, "os-reset_status", info);
	}
	@Override
	public Snapshot snapshot(String id, String name, String desc)
			throws OperationException {
		return snapshots.create(id, name, desc);
	}
	
}
