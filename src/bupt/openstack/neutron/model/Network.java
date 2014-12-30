package bupt.openstack.neutron.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("network")
public class Network extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Property("status")
	private String status;
	@Property("subnets")
	private List<String> subnets;
	@Property("provider:physical_network")
	private String physicalNetwork;
	@Property("admin_state_up")
	private boolean isUp;
	@Property("tenant_id")
	private String tenantId;
	@Property("provider:network_type")
	private String networkType;
	@Property("router:external")
	private boolean isExternal;
	@Property("shared")
	private boolean isShared;
	@Property("provider:segmentation_id")
	private String segmentationId;
	public Network() {
		super();
		this.isUp = true;
	}
	public Network(JSONObject json) {
		super(json);
	}
	public Network(String s) {
		this(new JSONObject(s));
	}
	public Network(Object o) {
		this(new JSONObject(o.toString()));
	}
	@Override
	public boolean isValid() {
		return networkType != null && physicalNetwork != null;
	}
	public String getStatus() {
		return status;
	}
	public List<String> getSubnets() {
		return Collections.unmodifiableList(this.subnets);
	}
	public String getPhysicalNetwork() {
		return physicalNetwork;
	}
	public boolean isUp() {
		return isUp;
	}
	public String getTenantId() {
		return tenantId;
	}
	public String getNetworkType() {
		return networkType;
	}
	public boolean isExternal() {
		return isExternal;
	}
	public boolean isShared() {
		return isShared;
	}
	public String getSegmentationId() {
		return segmentationId;
	}
	public void setPhysicalNetwork(String physicalNetwork) {
		this.physicalNetwork = physicalNetwork;
	}
	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public void setExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}
	public void setShared(boolean isShared) {
		this.isShared = isShared;
	}
	public void setSegmentationId(String segmentationId) {
		this.segmentationId = segmentationId;
	}
	
}
