package bupt.openstack.nova.api.v2;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.request.HttpMethod;
import bupt.openstack.common.request.Response;
import bupt.openstack.nova.api.ServerManager;
import bupt.openstack.nova.model.Console;
import bupt.openstack.nova.model.SecurityGroup;
import bupt.openstack.nova.model.Server;

public class Servers extends AbstractManager<Server> implements ServerManager{
	private final String PREFIX = "/servers";
	public Servers(Authenticated credentical) {
		super(credentical, Server.class);
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
	public List<Server> list() throws OperationException {
		return _list("/servers/detail");
	}
	@Override
	public Server get(String id) throws OperationException {
		return _get("/servers/" + id);
	}
	@Override
	public void addFixedIp(String id, String networkId)
			throws OperationException {
		Map<String, String> info = new HashMap<>(1);
		info.put("networkId", networkId);
		doAction(id, "addFixedIp", info);
	}
	@Override
	public void removeFixedIp(String id, String address)
			throws OperationException {
		Map<String, String> info = new HashMap<>(1);
		info.put("address", address);
		doAction(id, "removeFixedIp", info);
	}
	@Override
	public void stop(String id) throws OperationException {
		doAction(id, "os-stop");
	}
	@Override
	public void start(String id) throws OperationException {
		doAction(id, "os-start");
	}
	@Override
	public void reboot(String id, boolean hard) throws OperationException {
		Map<String, String> info = new HashMap<>(1);
		if (hard) {
			info.put("type", "HARD");
		} else {
			info.put("type", "SOFT");
		}
		doAction(id, "reboot", info);
	}
	@Override
	public void reboot(String id) throws OperationException {
		reboot(id, false);
	}
	@Override
	public Console getConsole(String id) throws OperationException {
		return new AbstractManager<Console>(credentical, Console.class) {
			public Console getConsole(String id) throws OperationException {
				Map<String, String> info = new HashMap<>(1);
				info.put("type", "novnc");
				JSONObject body = new JSONObject();
				body.put("os-getVNCConsole", info);
				return _post(PREFIX + "/" + id + "/action", body, Console.class);
			}
		}.getConsole(id);
	}
	@Override
	public String getLog(String id) throws OperationException {
		JSONObject info = new JSONObject();
		info.put("length", JSONObject.NULL);
		JSONObject body = new JSONObject();
		body.put("os-getConsoleOutput", info);
		Response response = null;
		try {
			response = client.doPost(getEndpoint() + PREFIX + "/" + id + "/action", body);
			if (!response.isSuccess()) {
				throw new OperationException(response.getBody());
			}
		} catch (Exception e) {
			throw new OperationException(e.getLocalizedMessage());
		}
		return new JSONObject(response.getBody()).getString("output");
	}
	@Override
	public void rename(String id, String name) throws OperationException {
		if (name == null)
			return;
		JSONObject info = new JSONObject();
		info.put("name", name);
		JSONObject body = new JSONObject();
		body.put("server", info);
		_update(PREFIX + "/" + id, body);
	}
	@Override
	public Server create(Server instance) throws OperationException {
		return _create(PREFIX, instance);
	}
	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);
	}
	@Override
	public String getPassword(String id, String privateKey) throws OperationException {
		Response response = null;
		String url = PREFIX + "/" + id + "/os-server-password";
		try {
			response = super.request(url, HttpMethod.GET, null);
		} catch (Exception e) {
			throw new OperationException(e);
		}
		if (!response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
		JSONObject password = new JSONObject(response.getBody());
		return password.getString("password");
	}
	@Override
	public void clearPassword(String id) throws OperationException {
		super._delete(PREFIX + "/" + id + "/os-server-password");
	}
	@Override
	public void forceDelete(String id) throws OperationException {
		doAction(id, "forceDelete", null);
	}
	
	
	@Override
	public void restore(String id) throws OperationException {
		doAction(id, "restore");
	}
	@Override
	public void pause(String id) throws OperationException {
		doAction(id, "pause");
		
	}
	@Override
	public void unpause(String id) throws OperationException {
		doAction(id, "unpause");
	}
	@Override
	public void lock(String id) throws OperationException {
		doAction(id, "lock");
	}
	@Override
	public void unlock(String id) throws OperationException {
		doAction(id, "unlock");
		
	}
	@Override
	public void resume(String id) throws OperationException {
		doAction(id, "resume");
		
	}
	@Override
	public void suspend(String id) throws OperationException {
		doAction(id, "suspend");
	}
	@Override
	public void rescue(String id) throws OperationException {
		doAction(id, "rescue");
		
	}
	@Override
	public void unrescue(String id) throws OperationException {
		doAction(id, "unrescue");
	}
	@Override
	public Map<String, String> diagnostics(String id)
			throws OperationException {
		Response response = null;
		String url = PREFIX + "/" + id + "/diagnostics";
		try {
			response = super.request(url, HttpMethod.GET, null);
		} catch (Exception e) {
			throw new OperationException(e);
		}
		if (!response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
		JSONObject data = new JSONObject(response.getBody());
		Map<String, String> values = new HashMap<>(data.length());
		for (Object key : data.keySet()) {
			values.put(key.toString(), data.get(key.toString()).toString());
		}
		return Collections.unmodifiableMap(values);
	}
	@Override
	public void rebuild(String id, String imageRef) throws OperationException {
		Objects.requireNonNull(imageRef);
		Map<String, String> body = new HashMap<>(1);
		body.put("imageRef", imageRef);
		doAction(id, "rebuild", body);
		
	}
	@Override
	public void migrate(String id) throws OperationException {
		doAction(id, "migrate");
		
	}
	@Override
	public void resize(String id, String flavorRef) throws OperationException {
		Objects.requireNonNull(flavorRef);
		Map<String, String> body = new HashMap<>(1);
		body.put("flavorRef", flavorRef);
		doAction(id, "resize", body);
		
	}
	@Override
	public void confirmResize(String id) throws OperationException {
		doAction(id, "confirmResize");
		
	}
	@Override
	public void revertResize(String id) throws OperationException {
		doAction(id, "revertResize");
		
	}
	@Override
	public void snapshot(String id, String name) throws OperationException {
		Objects.requireNonNull(name);
		Map<String, String> body = new HashMap<>(1);
		body.put("name", name);
		try {
			doAction(id, "createImage", body);
		} catch (Exception ignore) {
			
		}
	}
	@Override
	public void backup(String id, String name, BackupType type, int rotation)
			throws OperationException {
		Objects.requireNonNull(name);
		Map<String, String> body = new HashMap<>(1);
		body.put("name", name);
		body.put("backup_type", type.toString());
		body.put("rotation", String.valueOf(rotation));
		doAction(id, "createBackup", body);
	}
	@Override
	public void setMeta(String id, String key, String value)
			throws OperationException {
		JSONObject body = new JSONObject();
		JSONObject meta = new JSONObject();
		meta.put(key, value);
		body.put("metadata", meta);
		try {
			super._create(PREFIX + "/" + id + "/metadata", body);
		} catch (Exception e) {
			
		}
		
	}
	@Override
	public void updateMeta(String id, String key, String value) throws OperationException {
		setMeta(id, key, value);
	}
	@Override
	public void removeMeta(String id, String key) throws OperationException {
		super._delete(PREFIX + "/" + id + "/metadata/" + key);
	}
	@Override
	public void liveMigrate(String id, String hostname, boolean blockMigration, boolean diskOverCommit)
			throws OperationException {
		JSONObject body = new JSONObject();
		JSONObject info = new JSONObject();
		info.put("host", hostname);
		info.put("block_migration", blockMigration);
		info.put("disk_over_commit", diskOverCommit);
		body.put("os-migrateLive", info);
		try {
			super.request(PREFIX + "/" + id + "/action", HttpMethod.POST, body);
		} catch (Exception e) {
			throw new OperationException(e);
		}
	}
	@Override
	public void liveMigrate(String id, String hostname)
			throws OperationException {
		liveMigrate(id, hostname, true, true);
	}
	@Override
	public void resetState(String id, String state) throws OperationException {
		Objects.requireNonNull(state);
		assert(state.equals("error") || state.equals("active"));
		Map<String, String> body = new HashMap<>(1);
		body.put("state", state);
		doAction(id, "os-resetState", body);
	}
	@Override
	public void resetNetwork(String id) throws OperationException {
		doAction(id, "resetNetwork");
		
	}
	@Override
	public void addSecurityGroup(String id, String securityGroup)
			throws OperationException {
		Objects.requireNonNull(securityGroup);
		Map<String, String> body = new HashMap<>(1);
		body.put("name", securityGroup);
		doAction(id, "addSecurityGroup", body);
	}
	@Override
	public void removeSecurityGroup(String id, String securityGroup)
			throws OperationException {
		Objects.requireNonNull(securityGroup);
		Map<String, String> body = new HashMap<>(1);
		body.put("name", securityGroup);
		doAction(id, "removeSecurityGroup", body);
	}
	@Override
	public List<SecurityGroup> listSecurityGroup(String id)
			throws OperationException {
		return new AbstractManager<SecurityGroup>(this.credentical, SecurityGroup.class) {
			List<SecurityGroup> listSecurityGroup(String id) throws OperationException {
				return super._list(PREFIX + "/" + id + "/os-security-groups");
			}
		}.listSecurityGroup(id);
	}
	@Override
	public void evacuate(String id, String hostname) throws OperationException {
		Objects.requireNonNull(hostname);
		Map<String, String> body = new HashMap<>(2);
		body.put("host", hostname);
		body.put("onSharedStorage", String.valueOf(true));
		doAction(id, "evacuate", body);
	}
	
}
