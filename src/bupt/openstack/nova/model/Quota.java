package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.AnnotationProcessor;
import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.tools.Reflector;

import java.lang.reflect.*;

@Entity("quota_set")
public class Quota extends AbstractEntity {

	/**
	 * 
	 */
	private static final int UNINITIALIZED = -100;
	private static final int UNLIMITED = -1;
	private static final long serialVersionUID = -8337788208005358288L;
	@Property
	private int instances;
	@Property
	private int cores;
	@Property
	private int ram;
	@Property("floating_ips")
	private int floatingIps;
	@Property("fixed_ips")
	private int  fixedIps;
	@Property("metadata_items")
	private int metadataItems;
	@Property("injected_files")
	private int injectedFiles;
	@Property("injected_file_content_bytes")
	private int injectedFileContentBytes;
	@Property("injected_file_path_bytes")
	private int  injectedFilePathBytes;
	@Property("key_pairs")
	private int  keyPairs;
	@Property("security_groups")
	private int securityGroups;
	@Property("security_group_rules")
	private int securityGroupRules;
	@Property("tenant_id")
	private String tenantId;
	@Property
	private boolean force = true;
	public Quota() {
		super();
		instances = UNINITIALIZED;
		cores = UNINITIALIZED;
		ram = UNINITIALIZED;
		floatingIps = UNINITIALIZED;
		fixedIps = UNINITIALIZED;
		metadataItems = UNINITIALIZED;
		injectedFiles = UNINITIALIZED;
		injectedFileContentBytes = UNINITIALIZED;
		injectedFilePathBytes = UNINITIALIZED;
		keyPairs = UNINITIALIZED;
		securityGroups = UNINITIALIZED;
		securityGroupRules = UNINITIALIZED;
	}
	public Quota(JSONObject quota) {
		super(quota);
	}
	public Quota(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Quota(String s) {
		this(new JSONObject(s));
	}
	@Override
	public boolean isValid() {
		return true;
	}
	public int getMaxInstances() {
		return instances;
	}
	public void setMaxInstances(int instances) {
		this.instances = instances;
	}
	public int getMaxCores() {
		return cores;
	}
	public void setMaxCores(int cores) {
		this.cores = cores;
	}
	public int getMaxRam() {
		return ram;
	}
	public void setMaxRam(int ram) {
		this.ram = ram;
	}
	public int getMaxFloatingIps() {
		return floatingIps;
	}
	public void setMaxFloatingIps(int floatingIps) {
		this.floatingIps = floatingIps;
	}
	public int getMaxFixedIps() {
		return fixedIps;
	}
	public void setMaxFixedIps(int fixedIps) {
		this.fixedIps = fixedIps;
	}
	public int getMaxMetadataItems() {
		return metadataItems;
	}
	public void setMaxMetadataItems(int metadataItems) {
		this.metadataItems = metadataItems;
	}
	public int getMaxInjectedFiles() {
		return injectedFiles;
	}
	public void setMaxInjectedFiles(int injectedFiles) {
		this.injectedFiles = injectedFiles;
	}
	public int getMaxInjectedFileSize() {
		return injectedFileContentBytes;
	}
	public void setMaxInjectedFileSize(int injectedFileContentBytes) {
		this.injectedFileContentBytes = injectedFileContentBytes;
	}
	public int getMaxInjectedFilePathSize() {
		return injectedFilePathBytes;
	}
	public void setMaxInjectedFilePathSize(int injectedFilePathBytes) {
		this.injectedFilePathBytes = injectedFilePathBytes;
	}
	public int getMaxKeyPairs() {
		return keyPairs;
	}
	public void setMaxKeyPairs(int keyPairs) {
		this.keyPairs = keyPairs;
	}
	public int getMaxSecurityGroups() {
		return securityGroups;
	}
	public void setMaxSecurityGroups(int securityGroups) {
		this.securityGroups = securityGroups;
	}
	public int getMaxSecurityGroupRules() {
		return securityGroupRules;
	}
	public void setMaxSecurityGroupRules(int securityGroupRules) {
		this.securityGroupRules = securityGroupRules;
	}
	public int getUnLimitedValue() {
		return UNLIMITED;
	}
	public void setTenantId(String id) {
		this.tenantId = id;
	}
	public String getTenantId() {
		return this.tenantId;
	}
	@Override
	public JSONObject toJSONObject() {
		JSONObject quota = new JSONObject();
		JSONObject property = new JSONObject();
		for (Field field: Reflector.getFields(this)) {
			field.setAccessible(true);
			String name = AnnotationProcessor.getFieldName(field);
			if (name == null) {
				continue;
			}
			Object value = null;
			try {
				value = field.get(this);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (value == null) {
				//property.put(name, JSONObject.NULL);
				continue;
			}
			if (value instanceof Integer && Integer.parseInt(value.toString()) == UNINITIALIZED) {
				continue;
			}
			property.put(name, value);
		}
		quota.put(AnnotationProcessor.getEntityName(getClass()), property);
		return quota;
	}
}
