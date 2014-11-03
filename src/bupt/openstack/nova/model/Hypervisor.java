package bupt.openstack.nova.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class Hypervisor extends AbstractEntity {
	private static final long serialVersionUID = -4792220619450286178L;
	@Property("free_disk_gb")
	private int freeDisk;
	@Property("cpu_info")
	private CpuInfo cpuInfo;
	@Property("hypervisor_version")
	private long version;
	@Property("disk_available_least")
	private int diskAvailableLeast;
	@Property("local_gb")
	private int disk;
	@Property("free_ram_mb")
	private int freeRam;
	@Property("vcpus_used")
	private int vcpusUsed;
	@Property("hypervisor_type")
	private String type;
	@Property("local_gb_used")
	private int diskUsed;
	@Property("memory_mb_used")
	private int ramUsed;
	@Property("memory_mb")
	private int ram;
	@Property("current_workload")
	private int currentWorkload;
	@Property("vcpus")
	private int vcpus;
	@Property("running_vms")
	private int runningVms;
	@Property("service_id")
	private int serviceId;
	@Property("service_host")
	private String hostname;
	public Hypervisor() {
	}

	public Hypervisor(JSONObject hypervisor) {
		super(hypervisor);
		if (hypervisor.has("id")) {
			int _id = hypervisor.optInt("id", 0);
			this.id = String.valueOf(_id);
		}
		if (hypervisor.has("service")) {
			JSONObject service = hypervisor.getJSONObject("service");
			this.serviceId = service.optInt("id", 0);
			this.hostname = service.optString("host", "Unknown");
		}
	}

	public Hypervisor(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Hypervisor(String s) {
		this(new JSONObject(s));
	}
	public int getFreeDisk() {
		return freeDisk;
	}

	public CpuInfo getCpuInfo() {
		return cpuInfo;
	}

	public long getVersion() {
		return version;
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

	public int getVcpusUsed() {
		return vcpusUsed;
	}

	public String getType() {
		return type;
	}

	public int getDiskUsed() {
		return diskUsed;
	}

	public int getRamUsed() {
		return ramUsed;
	}

	public int getRam() {
		return ram;
	}

	public int getCurrentWorkload() {
		return currentWorkload;
	}

	public int getVcpus() {
		return vcpus;
	}

	public int getRunningVms() {
		return runningVms;
	}

	public int getServiceId() {
		return serviceId;
	}

	public String getHostname() {
		return hostname;
	}

	@Override
	public boolean isValid() {
		return true;
	}
	@Entity
	public static class Uptime extends AbstractEntity {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3161947862508894496L;
		@Property
		private int days;
		@Property
		private int munius;
		@Property
		private int hours;
		@Property("hypervisor_hostname")
		private String hostname;
		@Property("id")
		private String hostId;
		@Property
		private double[] loadAverage = new double[3];
		@Property
		private Date StatisticsTime;
		public Uptime() {
			super();
		}
		public Uptime(JSONObject json) {
			if (json.has("hypervisor_hostname")) {
				this.hostname = json.getString("hypervisor_hostname");
			}
			if (json.has("id")) {
				this.hostId = json.get("id").toString();
			}
			if (json.has("uptime")) {
				this.StatisticsTime = new Date();
				String uptime = json.getString("uptime");
				buildUptime(uptime);
			}
		}
		public Uptime(String s) {
			this(new JSONObject(s));
		}
		public Uptime(Object o) {
			this(o.toString());
		}
		private void buildUptime(String orgin) {
			String[] items = orgin.split(",");
			//System.out.println(Arrays.toString(items));
			String s = items[0].trim();
			int begin = s.indexOf("up") + 3;
			int end;
			String sub = null;
			if ((end = s.indexOf("days")) >= 0) {
				--end;
				sub = s.substring(begin, end);
				this.days = Integer.valueOf(sub);
			}
			s = items[1].trim();
			if (s.indexOf("min") >= 0 || s.indexOf(':') < 0) {
				end = s.indexOf("min");
				sub = s.substring(0, end);
				this.munius = Integer.valueOf(sub.trim());
				this.hours = 0;
			} else {
				String[] ss = s.split(":");
			//	System.out.println(Arrays.toString(ss));
				this.hours = Integer.valueOf(ss[0]);
				this.munius = Integer.valueOf(ss[1]);
			}
			begin = orgin.indexOf("load average:") + "load average:".length();
			end = orgin.length();
			sub = orgin.substring(begin, end).trim();
			items = sub.split(",");
			for (int i = 0; i < 3; ++i) {
				loadAverage[i] = Double.valueOf(items[i]);
			}
		}
		public int getDays() {
			return days;
		}
		public int getMunius() {
			return munius;
		}
		public int getHours() {
			return hours;
		}
		public String getHostname() {
			return hostname;
		}
		public String getHostId() {
			return hostId;
		}
		public Date getStatisticsTime() {
			return StatisticsTime;
		}
		
		public double[] getLoadAverage() {
			return Arrays.copyOf(loadAverage, 3);
		}
		@Override
		public boolean isValid() {
			return true;
		}
		
	}
	@Entity
	public static class CpuInfo extends AbstractEntity {
		/**
	 * 
	 */
		private static final long serialVersionUID = 5441864785475033869L;
		@Property
		private String vendor;
		@Property
		private String model;
		@Property
		private String arch;
		@Property
		private List<String> features;
		@Property
		private String topology;

		public CpuInfo() {
			super();
		}

		public CpuInfo(JSONObject entity) {
			super(entity);
		}

		public CpuInfo(Object obj) {
			this(new JSONObject(obj.toString()));
		}
		public CpuInfo(String s) {
			this(new JSONObject(s));
		}

		public String getVendor() {
			return this.vendor;
		}

		public String getModel() {
			return this.model;
		}

		public String getArch() {
			return this.arch;
		}

		public List<String> getFeatures() {
			if (this.features == null)
				this.features = new ArrayList<>();
			return Collections.unmodifiableList(this.features);
		}
		public String getTopology() {
			return this.topology;
		}
		@Override
		public boolean isValid() {
			return true;
		}
	}
}