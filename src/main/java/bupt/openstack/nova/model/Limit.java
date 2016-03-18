package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class Limit extends AbstractEntity {
	private static final long serialVersionUID = -6437814504661733389L;
	@Property
	private int maxServerMeta;
	@Property
	private int maxPersonality;
	@Property
	private int maxImageMeta;
	@Property
	private int maxPersonalitySize;
	@Property
	private int maxSecurityGroupRules;
	@Property
	private int maxTotalKeypairs;
	@Property
	private int maxSecurityGroups;
	@Property
	private int maxTotalRAMSize;
	@Property
	private int maxTotalCores;
	@Property
	private int maxTotalFloatingIps;
	@Property
	private int maxTotalInstances;
	@Property
	private int totalRAMUsed;
	@Property
	private int totalInstancesUsed;
	@Property
	private int totalFloatingIpsUsed;
	@Property
	private int totalSecurityGroupsUsed;
	@Property
	private int totalCoresUsed;
	public Limit() {
	}
	public Limit(JSONObject entity) {
		super(entity.getJSONObject("absolute"));
	}

	public Limit(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Limit(String s) {
		this(new JSONObject(s));
	}
	public int getMaxServerMeta() {
		return maxServerMeta;
	}
	public int getMaxPersonality() {
		return maxPersonality;
	}
	public int getMaxImageMeta() {
		return maxImageMeta;
	}
	public int getMaxPersonalitySize() {
		return maxPersonalitySize;
	}
	public int getMaxSecurityGroupRules() {
		return maxSecurityGroupRules;
	}
	public int getMaxTotalKeypairs() {
		return maxTotalKeypairs;
	}
	public int getMaxSecurityGroups() {
		return maxSecurityGroups;
	}
	public int getMaxTotalRAMSize() {
		return maxTotalRAMSize;
	}
	public int getMaxTotalCores() {
		return maxTotalCores;
	}
	public int getMaxTotalFloatingIps() {
		return maxTotalFloatingIps;
	}
	public int getMaxTotalInstances() {
		return maxTotalInstances;
	}
	public int getTotalRAMUsed() {
		return totalRAMUsed;
	}
	public int getTotalInstancesUsed() {
		return totalInstancesUsed;
	}
	public int getTotalFloatingIpsUsed() {
		return totalFloatingIpsUsed;
	}
	public int getTotalSecurityGroupsUsed() {
		return totalSecurityGroupsUsed;
	}
	public int getTotalCoresUsed() {
		return totalCoresUsed;
	}
	@Override
	public boolean isValid() {
		return true;
	}
}