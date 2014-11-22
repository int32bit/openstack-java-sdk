package bupt.openstack.test;

import bupt.openstack.common.OpenstackSession;
import bupt.openstack.common.OperationException;
import bupt.openstack.nova.Nova;

public class SessionTest {
	public static void main(String[] args) throws OperationException {
		OpenstackSession session = OpenstackSession.getSession("admin", "ADMIN_PASS");
		Nova nova = session.getNovaClient();
		System.out.println(nova.hypervisors.uptime("2").toString(4));
		//System.out.println(nova.images.get("d9475eec-d319-494f-921f-58ec1e229ded").toString(4));
		
	}
}
