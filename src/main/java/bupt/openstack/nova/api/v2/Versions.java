package bupt.openstack.nova.api.v2;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.request.Response;
import bupt.openstack.common.tools.JSONConverter;
import bupt.openstack.nova.api.VersionManager;
import bupt.openstack.nova.model.Version;

public class Versions extends AbstractManager<Version> implements VersionManager {

	public Versions(Authenticated credentical) {
		super(credentical, Version.class);
	}

	@Override
	public List<Version> list() throws OperationException {
		URL url = null;
		StringBuilder sb = new StringBuilder();
		try {
			url = new URL(getEndpoint());
			sb.append(url.getProtocol() + "://");
			sb.append(url.getHost());
			sb.append(":" + url.getPort());
		} catch (MalformedURLException ignore) {
			
		}
		String addr = sb.toString();
		Response response = null;
		try {
			response = super.client.doGet(addr);
			if (!response.isSuccess()) {
				throw new OperationException(response.getBody());
			}
		} catch(Exception e) {
			throw new OperationException(e.getLocalizedMessage());
		}
		return JSONConverter.responseToEntityList(response.getBody(), Version.class, getRegion());
	}

}
