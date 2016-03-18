package bupt.openstack.neutron.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("subnet")
public class Subnet extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Property("enable_dhcp")
	private boolean enableDHCP;
	@Property("network_id")
	private String networkId;
	@Property("tenant_id")
	private String tenantId;
	@Property("dns_nameservers")
	private List<String> nameServers;
	@Property("gateway_ip")
	private String gateway;
	@Property("ipv6_ra_mode")
	private String ipv6RAMode;
	@Property("allocation_pools")
	private List<Range> allocationPools;
	@Property("host_routes")
	private List<Route> hostRoutes;
	@Property("ip_version")
	private String ipVersion;
	@Property("ipv6_address_mode")
	private String ipv6AddressMode;
	@Property("cidr")
	private String CIDR;
	@Override
	public boolean isValid() {
		return true;
	}
	public Subnet(JSONObject json) {
		super(json);
	}
	public Subnet(String s) {
		this(new JSONObject(s));
	}
	public Subnet(Object o) {
		this(o.toString());
	}
	public Subnet() {
		super();
		this.enableDHCP = true;
		this.ipVersion = "4";
	}
	
	public boolean isEnableDHCP() {
		return enableDHCP;
	}
	public String getNetworkId() {
		return networkId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public List<String> getNameServers() {
		return Collections.unmodifiableList(this.nameServers);
	}
	public String getGateway() {
		return gateway;
	}
	public String getIpv6RAMode() {
		return ipv6RAMode;
	}
	public List<Range> getAllocationPools() {
		return Collections.unmodifiableList(this.allocationPools);
	}
	public List<Route> getHostRoutes() {
		return hostRoutes;
	}
	public String getIpVersion() {
		return ipVersion;
	}
	public String getIpv6AddressMode() {
		return ipv6AddressMode;
	}
	public String getCIDR() {
		return CIDR;
	}
	public void setEnableDHCP(boolean enableDHCP) {
		this.enableDHCP = enableDHCP;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public void setNameServers(List<String> nameServers) {
		if (this.nameServers == null)
			this.nameServers = new ArrayList<>();
		this.nameServers.addAll(nameServers);
	}
	public void addNameServer(String nameserver) {
		if (this.nameServers == null)
			this.nameServers = new ArrayList<>();
		this.nameServers.add(nameserver);
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public void setIpv6RAMode(String ipv6raMode) {
		ipv6RAMode = ipv6raMode;
	}
	public void setAllocationPools(List<Range> allocationPools) {
		if (this.allocationPools == null)
			this.allocationPools = new ArrayList<>(allocationPools.size());
		this.allocationPools.addAll(allocationPools);
	}
	public void addAllocationPool(Range range) {
		if (this.allocationPools == null)
			this.allocationPools = new ArrayList<>();
		this.allocationPools.add(range);
	}
	public void addAllocationPool(String start, String end) {
		addAllocationPool(new Range(start, end));
	}
	public void setHostRoutes(List<Route> hostRoutes) {
		if (this.hostRoutes == null)
			this.hostRoutes = new ArrayList<>(hostRoutes.size());
		hostRoutes.addAll(hostRoutes);
	}
	public void addHostRoute(Route route) {
		if (this.hostRoutes == null)
			this.hostRoutes = new ArrayList<>();
		hostRoutes.add(route);
	}
	public void addHostRoute(String destination, String nexthop) {
		addHostRoute(new Route(destination, nexthop));
	}
	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}
	public void setIpv6AddressMode(String ipv6AddressMode) {
		this.ipv6AddressMode = ipv6AddressMode;
	}
	public void setCIDR(String cIDR) {
		CIDR = cIDR;
	}

	@Entity
	public static class Range extends AbstractEntity {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Property("start")
		private String start;
		@Property("end")
		private String end;
		@Override
		public boolean isValid() {
			return start != null && end != null;
		}
		public Range(JSONObject json) {
			super(json);
		}
		public Range(String s) {
			this(new JSONObject(s));
		}
		public Range(Object o) {
			this(o.toString());
		}
		public Range(String start, String end) {
			this.start = start;
			this.end = end;
		}
		public String getStart() {
			return start;
		}
		public String getEnd() {
			return end;
		}
		public void setStart(String start) {
			this.start = start;
		}
		public void setEnd(String end) {
			this.end = end;
		}
	}
	@Entity
	public static class Route extends AbstractEntity {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7474840790469386612L;
		@Property("nexthop")
		private String nexthop;
		@Property("destination")
		private String destination;
		public Route(JSONObject json) {
			super(json);
		}
		public Route(String s) {
			this(new JSONObject(s));
		}
		public Route(Object o) {
			this(o.toString());
		}
		public Route(String destination, String nexthop) {
			this.destination = destination;
			this.nexthop = nexthop;
		}
		@Override
		public boolean isValid() {
			return true;
		}
		public String getNexthop() {
			return nexthop;
		}
		public String getDestination() {
			return destination;
		}
		public void setNexthop(String nexthop) {
			this.nexthop = nexthop;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
	}
}
