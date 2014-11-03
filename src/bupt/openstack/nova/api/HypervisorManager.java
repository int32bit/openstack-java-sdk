package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Hypervisor;
import bupt.openstack.nova.model.Hypervisor.Uptime;
import bupt.openstack.nova.model.HypervisorStatistics;

public interface HypervisorManager 	{
	List<Hypervisor> list() throws OperationException;
	Hypervisor get(String id) throws OperationException;
	Uptime uptime(String id) throws OperationException;
	HypervisorStatistics stats() throws OperationException;
}
