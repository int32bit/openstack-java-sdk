package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class FloatingIp extends AbstractEntity implements Comparable<FloatingIp> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6337162712126341229L;
	@Property
	private String address;
	@Property("interface")
	private String nic;
	@Property("instance_uuid")
	private String instanceId;
	@Property("project_id")
	private String tenantId;
	@Property("pool")
	private String pool;
	
	public FloatingIp() {
		super();
	}

	public FloatingIp(JSONObject json) {
		super(json);
		this.id = this.address + "@" + this.pool;
		this.name = this.address;
	}

	public FloatingIp(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public FloatingIp(String s) {
		this(new JSONObject(s));
	}
	public String getAddress() {
		return address;
	}

	public String getNic() {
		return nic;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getPool() {
		return pool;
	}

	@Override
	public boolean isValid() {
		return true;
	}
	@Override
	public int compareTo(FloatingIp o) {
		String addr1 = this.getAddress();
		String addr2 = o.getAddress();
		String[] slice1 = addr1.split("\\.");
		String[] slice2 = addr2.split("\\.");
		for (int i = 0; i < slice1.length; ++i) {
			int a = Integer.valueOf(slice1[i]);
			int b = Integer.valueOf(slice2[i]);
			if (a != b) {
				return a - b;
			}
		}
		return 0;
	}
}
