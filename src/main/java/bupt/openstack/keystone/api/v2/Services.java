package bupt.openstack.keystone.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.api.ServiceManager;
import bupt.openstack.keystone.model.Service;

public class Services extends AbstractManager<Service> implements ServiceManager {
	private static final String PREFIX = "/OS-KSADM/services";
	public Services(Authenticated credentical) {
		super(credentical, Service.class);
	}

	@Override
	public List<Service> list() throws OperationException {
		return super._list(PREFIX);
	}

	@Override
	public Service get(String id) throws OperationException {
		return super._get(PREFIX + "/" + id);
	}

	@Override
	public Service create(String name, String type, String description)
			throws OperationException {
		Service service = new Service();
		service.setName(name);
		service.setType(type);
		service.setDescription(description);
		return create(service);
	}
	@Override
	public Service create(Service service) throws OperationException {
		return super._create(PREFIX, service);
	}

	@Override
	public void delete(String id) throws OperationException {
		/** FIXME 删除成功也会抛出异常 **/
		try {
			super._delete(PREFIX + "/" + id);
		} catch (Exception ignore) {
			
		}
	}

}
