package bupt.openstack.nova.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import bupt.openstack.common.annotation.AnnotationProcessor;
import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.Link;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.tools.Base64;
import bupt.openstack.common.tools.JSONConverter;
@Entity("server")
public class Server extends AbstractEntity {
	private static final long serialVersionUID = 3928209492480174078L;
	@Property("status")
	private String status;
	@Property
	private String updated;
	@Property
	private String hostId;
	@Property("OS-EXT-SRV-ATTR:host")
	private String host;
	@Property
	private List<Link> links;
	@Property("key_name")
	private String keyName;
	@Property("OS-EXT-STS:task_state")
	private String taskState;
	@Property("vm_state")
	private String vmState;
	@Property("OS-EXT-SRV-ATTR:instance_name")
	private String instanceName;
	@Property("OS-SRV-USG:launched_at")
	private String launchedAt;
	@Property("OS-EXT-SRV-ATTR:hypervisor_hostname")
	private String hypervisorHostname;
	@Property("security_groups")
	private List<Group> securityGroups;
	@Property("OS-SRV-USG:terminated_at")
	private String terminatedAt;
	@Property("OS-EXT-AZ:availability_zone")
	private String availabilityZone;
	@Property("user_id")
	private String userId;
	@Property
	private String created;
	@Property("tenant_id")
	private String tenantId;
	@Property("OS-DCF:diskConfig")
	private String diskConfig;
	@Property("os-extended-volumes:volumes_attached")
	private List<String> volumesAttached;
	@Property
	private String accessIPv4;
	@Property
	private String accessIPv6;
	@Property
	private int progress;
	@Property("OS-EXT-STS:power_state")
	private int powerState;
	@Property("config_drive")
	private String configDrive;
	@Property
	private String metadata;
	@Property
	private List<Address> addresses;
	@Property
	private String imageRef;
	@Property
	private String flavorRef;
	@Property
	private String adminPass;
	private int count = 1;
	@Property("min_count")
	private int minCount = 1;
	@Property("max_count")
	private int maxCount = 1;
	@Property("user_data")
	private String userData;
	@Property
	private List<InjectFile> personality;
	public Server() {
		super();
	}
	public Server(JSONObject instance) {
		super(instance);
		if (instance.has("image")) {
			imageRef = instance.getJSONObject("image").getString("id");
		}
		if (instance.has("flavor")) {
			flavorRef = instance.getJSONObject("flavor").getString("id");
		}
		String addresses = AnnotationProcessor.getFieldName(getClass(), "addresses");
		assert(addresses != null);
		if (!instance.has(addresses))
			return;
		if (this.addresses == null)
			this.addresses = new ArrayList<>();
		JSONObject _addresses = instance.getJSONObject("addresses");
		for (Object netName : _addresses.keySet()) {
			JSONArray values = _addresses.getJSONArray((String)netName);
			if (values == null)
				continue;
			for (int i = 0; i < values.length(); i++) {
				Address addr = new Address(values.optJSONObject(i));
				this.addresses.add(addr);
			}
		}
	}

	public Server(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Server(String s) {
		this(new JSONObject(s));
	}

	public Server setCount(int count) {
		this.count = count;
		this.minCount = 1;
		this.maxCount = count;
		return this;
	}
	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public List<Group> getSecurityGroups() {
		if (securityGroups == null)
			securityGroups = new ArrayList<>();
		return Collections.unmodifiableList(securityGroups);
		//return securityGroups;
	}

	public void setSecurityGroups(List<Group> securityGroups) {
		if (securityGroups == null)
			securityGroups = new ArrayList<>();
		this.securityGroups.addAll(securityGroups);
	}
	public void appendSecurityGroupsWithName(String name) {
		if (this.securityGroups == null)
			securityGroups = new ArrayList<>();
		securityGroups.add(new Group(name));
	}
	public void appendSecurityGroup(SecurityGroup group) {
		if (this.securityGroups == null)
			securityGroups = new ArrayList<>();
		securityGroups.add(new Group(group.getName()));
	}
	public String getDiskConfig() {
		return diskConfig;
	}

	public void setDiskConfig(String diskConfig) {
		this.diskConfig = diskConfig;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		if (this.addresses == null)
			this.addresses = new ArrayList<>();
		this.addresses.addAll(addresses);
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public String getFlavorRef() {
		return flavorRef;
	}

	public void setFlavorRef(String flavorRef) {
		this.flavorRef = flavorRef;
	}

	public int getMinCount() {
		return minCount;
	}

	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	public List<InjectFile> getPersonality() {
		if (this.personality == null)
			this.personality = new ArrayList<>();
		return Collections.unmodifiableList(this.personality);
	}

	public void setPersonality(List<InjectFile> personality) {
		if (this.personality == null)
			this.personality = new ArrayList<>();
		this.personality.addAll(personality);
	}

	public String getStatus() {
		return status;
	}

	public String getUpdated() {
		return updated;
	}

	public String getHostId() {
		return hostId;
	}

	public String getHost() {
		return host;
	}

	public List<Link> getLinks() {
		if (links == null)
			links = new ArrayList<>();
		return Collections.unmodifiableList(links);
	}

	public String getTaskState() {
		return taskState;
	}

	public String getVmState() {
		return vmState;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public String getLaunchedAt() {
		return launchedAt;
	}

	public String getHypervisorHostname() {
		return hypervisorHostname;
	}

	public String getTerminatedAt() {
		return terminatedAt;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}
	public void setAvailabilityZone(String zone) {
		this.availabilityZone = zone;
	}

	public String getUserId() {
		return userId;
	}

	public String getCreated() {
		return created;
	}

	public String getTenantId() {
		return tenantId;
	}

	public List<String> getVolumesAttached() {
		if (this.volumesAttached == null)
			this.volumesAttached = new ArrayList<>();
		return Collections.unmodifiableList(this.volumesAttached);
	}

	public String getAccessIPv4() {
		return accessIPv4;
	}

	public String getAccessIPv6() {
		return accessIPv6;
	}

	public int getProgress() {
		return progress;
	}

	public int getPowerState() {
		return powerState;
	}

	public String getConfigDrive() {
		return configDrive;
	}

	public String getMetadata() {
		return metadata;
	}

	public int getCount() {
		return count;
	}
	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public void setUserData(String userData) {
		this.userData = userData;
	}
	private String readFile(File file) {
		StringBuilder sb = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
				sb.append('\n');
			}
		} catch (Exception localException) {
			if (in != null)
				try {
					in.close();
				} catch (IOException localIOException) {
				}
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException localIOException1) {
				}
		}
		return sb.toString();
	}

	public Server addInjectFile(String content, String targetPath) {
		if (this.personality == null)
			this.personality = new ArrayList<>();
		InjectFile file = new InjectFile(Base64.toBase64(content),
				targetPath);
		this.personality.add(file);
		return this;
	}

	public Server addInjectFile(File file, String targetPath) {
		String content = readFile(file);
		addInjectFile(content, targetPath);
		return this;
	}

	public String getUserData() {
		return this.userData;
	}

	@Override
	public boolean isValid() {
		if ((this.minCount < 1) || (this.maxCount < 1)
				|| (this.minCount > this.maxCount))
			return false;
		if ((this.name == null) || (this.name.length() < 1))
			return false;
		return true;
	}
	public static class Diagnostics {
		private Map<String, Integer> diagnostics;

		public Diagnostics() {
			this.diagnostics = new HashMap<>();
		}

		public Diagnostics(JSONObject json) {
			this.diagnostics = JSONConverter.jsonToMap(json);
		}

		public Diagnostics(Object obj) {
			this(new JSONObject(obj.toString()));
		}

		public Map<String, Integer> getDiagnostics() {
			return this.diagnostics;
		}

		@Override
		public String toString() {
			return JSONConverter.mapToJSON(this.diagnostics).toString();
		}

		public String toString(int i) {
			return JSONConverter.mapToJSON(this.diagnostics).toString(i);
		}

		public int get(String what) {
			return ((Integer) this.diagnostics.get(what)).intValue();
		}

		public Set<String> keySet() {
			return this.diagnostics.keySet();
		}
	}

	public static class Group extends AbstractEntity {
		private static final long serialVersionUID = -5598050879848859356L;

		public Group() {
		}
		public Group(String name) {
			this.name = name;
		}
		public Group(JSONObject entity) {
			super(entity);
		}

		public Group(Object obj) {
			super(obj);
		}
		@Override
		public boolean isValid() {
			return this.name != null;
		}

		@Override
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String toString() {
			JSONObject json = new JSONObject();
			json.put("name", this.name);
			return json.toString();
		}
	}
	@Entity
	private class InjectFile extends AbstractEntity {
		/**
	 * 
	 */
		private static final long serialVersionUID = -6311667628963149394L;
		@Property
		private String path;
		@Property
		private String content;

		@SuppressWarnings("unused")
		public InjectFile() {
		}

		public InjectFile(String content, String target) {
			this.path = target;
			this.content = content;
		}

		@Override
		public String toString() {
			JSONObject json = new JSONObject();
			json.put("path", this.path);
			json.put("contents", this.content);
			return json.toString();
		}
		@Override
		public boolean isValid() {
			return this.path != null;
		}
	}
	@Entity("address")
	public static class Address extends AbstractEntity {
		/**
	 * 
	 */
		private static final long serialVersionUID = -3311116474141575179L;
		@Property("OS-EXT-IPS-MAC:mac_addr")
		private String macAddr;
		@Property
		private String addr;
		@Property
		private int version;
		@Property("OS-EXT-IPS:type")
		private String type;

		public Address(JSONObject address) {
			super(address);
			/*if (address.has("OS-EXT-IPS-MAC:mac_addr")) {
				this.macAddr = 
				setMacAddr(address.optString("OS-EXT-IPS-MAC:mac_addr", null));
			}
			if (address.has("OS-EXT-IPS:type"))
				setType(address.optString("OS-EXT-IPS:type", null));*/
		}
		public Address(Object o) {
			super(o);
		}
		public Address(String s) {
			super(s);
		}
		public String getMacAddr() {
			return this.macAddr;
		}
		public String getAddr() {
			return this.addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		public int getVersion() {
			return this.version;
		}

		public void setVersion(int version) {
			this.version = version;
		}

		public String getType() {
			return this.type;
		}

		public void setType(String type) {
			this.type = type;
		}

		@Override
		public boolean isValid() {
			return true;
		}
		
	}
}
