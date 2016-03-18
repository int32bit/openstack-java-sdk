package bupt.openstack.cinder.api.v1;

import java.util.List;

import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.cinder.api.ServiceManager;
import bupt.openstack.cinder.model.Service;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.request.HttpMethod;
import bupt.openstack.common.request.Response;
import bupt.openstack.common.tools.JSONConverter;

public class Services extends AbstractManager<Service> implements ServiceManager {
	private static final String PREFIX = "/os-services";
	public Services(Authenticated credentical) {
		super(credentical, Service.class);
	}
	private Service doAction(String host, String name, String action) throws OperationException {
		JSONObject body = new JSONObject();
		body.put("host", host);
		body.put("binary", name);
		Response response = null;
		try {
			response = super.request(PREFIX + "/" + action, HttpMethod.PUT, body);
		} catch(Exception e) {
			throw new OperationException(e);
		}
		if (!response.isSuccess())
			throw new OperationException(response.getBody());
		return JSONConverter.responseToEntity(response.getBody(), "", Service.class, getRegion());
	}
	@Override
	public List<Service> list() throws OperationException {
		return _list(PREFIX);
	}
	@Override
	public Service enable(String host, String name) throws OperationException {
		return doAction(host, name, "enable");
	}

	@Override
	public Service disalbe(String host, String name) throws OperationException {
		return doAction(host, name, "disable");
	}

}
