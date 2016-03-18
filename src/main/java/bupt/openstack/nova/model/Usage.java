package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class Usage extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8936799617566878613L;
	@Property("total_memory_mb_usage")
	private double ram;
	@Property("total_vcpus_usage")
	private double vcpus;
	@Property
	private String start;
	@Property
	private String stop;
	@Property("tenant_id")
	private String tenantId;
	@Property("tenantName")
	private String tenant_name;
	@Property("total_hours")
	private double hours;
	@Property("total_local_gb_usage")
	private double disk;

	public Usage() {
	}

	public Usage(JSONObject entity) {
		super(entity);
	}

	public Usage(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Usage(String s) {
		this(new JSONObject(s));
	}
	public double getRam() {
		return ram;
	}

	public double getVcpus() {
		return vcpus;
	}

	public String getStart() {
		return start;
	}

	public String getStop() {
		return stop;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getTenant_name() {
		return tenant_name;
	}

	public double getHours() {
		return hours;
	}

	public double getDisk() {
		return disk;
	}

	@Override
	public boolean isValid() {
		return true;
	}
}