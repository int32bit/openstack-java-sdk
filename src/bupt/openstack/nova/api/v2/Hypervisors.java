package bupt.openstack.nova.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.nova.api.HypervisorManager;
import bupt.openstack.nova.model.Hypervisor;
import bupt.openstack.nova.model.Hypervisor.Uptime;
import bupt.openstack.nova.model.HypervisorStatistics;

public class Hypervisors extends AbstractManager<Hypervisor> implements HypervisorManager {
	private final String PREFIX = "/os-hypervisors";
	public Hypervisors(Authenticated credentical) {
		super(credentical, Hypervisor.class);
	}
	@Override
	public List<Hypervisor> list() throws OperationException {
		return _list(PREFIX + "/detail");
	}
	@Override
	public Hypervisor get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}
	@Override
	public Uptime uptime(String id) throws OperationException {
		return new Uptimes(credentical).uptime(id);
	}
	@Override
	public HypervisorStatistics stats() throws OperationException {
		return new Statistics(credentical).stats();
	}
	public static class Uptimes extends AbstractManager<Uptime> {
		public Uptimes(Authenticated credentical) {
			super(credentical, Uptime.class);
		}
		public Uptime uptime(String id) throws OperationException {
			String api = "/os-hypervisors/%s/uptime";
			return _get(String.format(api, id));
		}
	}
	public static class Statistics extends AbstractManager<HypervisorStatistics> {
		public Statistics(Authenticated credentical) {
			super(credentical,HypervisorStatistics.class);
		}
		public HypervisorStatistics stats() throws OperationException {
			return _get("/os-hypervisors/statistics");
		}
	}
}
