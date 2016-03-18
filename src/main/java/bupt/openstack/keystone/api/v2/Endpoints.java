package bupt.openstack.keystone.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.api.EndpointManager;
import bupt.openstack.keystone.model.Endpoint;

public class Endpoints extends AbstractManager<Endpoint> implements EndpointManager {
	private static final String PREFIX = "/endpoints";
	public Endpoints(Authenticated credentical) {
		super(credentical, Endpoint.class);
	}

	@Override
	public List<Endpoint> list() throws OperationException {
		return _list(PREFIX);
	}

	@Override
	public Endpoint create(Endpoint endpoint) throws OperationException {
		return _create(PREFIX, endpoint);
	}
	@Override
	public Endpoint create(String region, String serviceId, String publicurl,
			String adminurl, String internalurl) throws OperationException {
		Endpoint endpoint = new Endpoint();
		endpoint.setRegion(region);
		endpoint.setAdminURL(adminurl);
		endpoint.setInternalURL(internalurl);
		endpoint.setServiceId(serviceId);
		endpoint.setPublicURL(publicurl);
		return create(endpoint);
	}
	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);
	}

	

}
