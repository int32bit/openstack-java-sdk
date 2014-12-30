package bupt.openstack.test;

import bupt.openstack.common.OpenstackSession;
import bupt.openstack.common.OperationException;
import bupt.openstack.neutron.Neutron;
import bupt.openstack.neutron.model.Subnet;

public class SessionTest {
	public static void main(String[] args) throws OperationException {
		OpenstackSession session = OpenstackSession.getSession("hadoop", "admin", "ADMIN_PASS");
		Neutron neutron = session.getNeutronClient();
		System.out.println(neutron.subnets.get("77abe006-c62d-46a5-825a-36b92eda0c65").toString(4));
		for (Subnet subnet : neutron.subnets.list()) {
			//System.out.println(subnet.toString(4));
		}
	}
}
