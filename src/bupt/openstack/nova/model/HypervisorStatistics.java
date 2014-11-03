package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("hypervisor_statistics")
public class HypervisorStatistics extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3306512943313321327L;
	@Property
	private int count;
	@Property("vcpus_used")
	private int vcpusUsed;
	@Property("local_gb_used")
	private int diskUsed;
	@Property("memory_mb")
	private int ram;
	@Property("current_workload")
	private double currentWorkload;
	@Property
	private int vcpus;
	@Property("running_vms")
	private int runningVms;
	@Property("free_disk_gb")
	private int freeDisk;
	@Property("disk_available_least")
	private int diskAvailableLeast;
	@Property("local_gb")
	private int disk;
	@Property("free_ram_mb")
	private int freeRam;
	@Property("memory_mb_used")
	private int ramUsed;
	public HypervisorStatistics(JSONObject json) {
		super(json);
	}
	public HypervisorStatistics(Object obj) {
		super(new JSONObject(obj.toString()));
	}
	public HypervisorStatistics(String s) {
		this(new JSONObject(s));
	}
	@Override
	public boolean isValid() {
		return true;
	}
	public int getCount() {
		return count;
	}
	public int getVcpusUsed() {
		return vcpusUsed;
	}
	public int getDiskUsed() {
		return diskUsed;
	}
	public int getRam() {
		return ram;
	}
	public double getCurrentWorkload() {
		return currentWorkload;
	}
	public int getVcpus() {
		return vcpus;
	}
	public int getRunningVms() {
		return runningVms;
	}
	public int getFreeDisk() {
		return freeDisk;
	}
	public int getDiskAvailableLeast() {
		return diskAvailableLeast;
	}
	public int getDisk() {
		return disk;
	}
	public int getFreeRam() {
		return freeRam;
	}
	public int getRamUsed() {
		return ramUsed;
	}
}
