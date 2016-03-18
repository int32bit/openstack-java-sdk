package bupt.openstack.test;

import bupt.openstack.common.OpenstackSession;
import bupt.openstack.common.OperationException;
import bupt.openstack.neutron.Neutron;
import bupt.openstack.neutron.model.Network;

public class SessionTest {
	public static void main(String[] args) throws OperationException {
		OpenstackSession session = OpenstackSession.getSession("hadoop", "admin", "ADMIN_PASS");
		Neutron neutron = session.getNeutronClient();
		Network network = neutron.networks.get("8e3b4f41-7285-4ab0-93b3-09ab5776c8c9");
		network.setShared(false);
		network.setUp(true);
		network.setName("NEWNAME111");
		neutron.networks.update(network);
		for (Network net : neutron.networks.list()) {
			System.out.println(net.toString(4));
		}
	}
}
