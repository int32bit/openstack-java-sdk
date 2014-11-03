package bupt.openstack.common;

import static bupt.openstack.common.tools.JSONConverter.responseToEntity;
import static bupt.openstack.common.tools.JSONConverter.responseToEntityList;
import static bupt.openstack.common.tools.Math.notIn;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.authentication.AuthenticatedRequest;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.request.Header;
import bupt.openstack.common.request.OpenstackHTTPClient;
import bupt.openstack.common.request.Response;
import bupt.openstack.common.request.HttpMethod;
public abstract class Manager<T extends AbstractEntity> {
	protected AuthenticatedRequest client;
	private Class<T> objClass;
	protected Authenticated credentical;
	public Manager(Authenticated credentical, Class<T> objClass) {
		this.credentical = credentical;
		this.client = new OpenstackHTTPClient(credentical);
		this.objClass = objClass;
	}
	protected abstract String getEndpoint();
	protected String getRegion() {
		return credentical.getWorkRegion();
	}
	protected Response request(String url,HttpMethod method, Object data) throws Exception {
		url = getEndpoint() + url;
		Response response = null;
		switch(method) {
		case GET:
			response = client.doGet(url);
			break;
		case POST:
			response = client.doPost(url, data);
			break;
		case PUT:
			response = client.doPut(url, data);
			break;
		case DELETE:
			response = client.doDelete(url);
			break;
		case HEAD:
			response = client.doHead(url);
			break;
		default:
			throw new OperationException("Unsuported http method request!");
		}
		if (response != null && !response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
		return response;
	}
	protected Response request(Header header, String url, HttpMethod method, Object data) throws Exception {
		url = getEndpoint() + url;
		Response response = null;
		switch(method) {
		case GET:
			response = client.doGet(header, url);
			break;
		case POST:
			response = client.doPost(header, url, data);
			break;
		case PUT:
			response = client.doPut(header, url, data);
			break;
		case DELETE:
			response = client.doDelete(header, url);
			break;
		case HEAD:
			response = client.doHead(header, url);
			break;
		default:
			throw new OperationException("Unsuported http method request!");
		}
		if (response != null && !response.isSuccess()) {
			throw new OperationException(response.getBody());
		}
		return response;
	}
	/* ****************************************** list ***********************************/
	protected List<T> _list(String what, String url, Object data) throws OperationException {
		Response response = null;
		url = getEndpoint() + url;
		try {
			if (data == null) {
				response = client.doGet(url);
			} else {
				response = client.doPost(url, data);
			}
		} catch (Exception e) {
			throw new OperationException(e);
		}
		if (notIn(response.getCode(), 200, 300)) {
			throw new OperationException(response.getBody());
		}
		if (what != null)
			return responseToEntityList(response.getBody(),what, objClass, getRegion());
		else
			return responseToEntityList(response.getBody(), objClass, getRegion());
	}
	protected List<T> _list(String url, Object data) throws OperationException {
		return _list(null, url, data);
	}
	protected List<T> _list(String what, String url) throws OperationException {
		return _list(what, url, null);
	}
	protected List<T> _list(String url) throws OperationException {
		return _list(null, url, null);
	}
	/* ********************************************* get **************************************** */
	protected T _get(String url, String what) throws OperationException {
		return _get(url, what, objClass);
	}
	protected T _get(String url) throws OperationException {
		return _get(url, null, objClass);
	}
	protected <K extends AbstractEntity>K _get(String url, Class<K>cl) throws OperationException {
		return _get(url, null, cl);
	}
	protected <K extends AbstractEntity> K _get(String url, String what, Class<K> cl) throws OperationException {
		Response response = null;
		url = getEndpoint() + url;
		try {
			response = client.doGet(url);
		} catch (Exception ex) {
			throw new OperationException(ex.getMessage());
		}
		int code = response.getCode();
		String body = response.getBody();
		if (notIn(code, 200, 300)) {
			throw new OperationException(body);
		}
		return responseToEntity(body, what, cl, getRegion());
	}
	/* ****************************************** delete ********************************** */
	protected void _delete(String url) throws OperationException {
		Response response = null;
		url = getEndpoint() + url;
		try {
			response = client.doDelete(url);
		} catch (Exception ex) {
			throw new OperationException(ex);
		}
		int code = response.getCode();
		String body = response.getBody();
		if (!response.isSuccess()) {
			throw new OperationException("%d : %s", code, body);
		}
	}
	/* ************************************* update ************************************ */
	protected T _update(String url, Object data) throws OperationException {
		Response response = null;
		url = getEndpoint() + url;
		try {
			response = client.doPut(url, data);
		} catch (Exception ex) {
			throw new OperationException(ex.getMessage());
		}
		int code = response.getCode();
		String body = response.getBody();
		if (notIn(code, 200, 300)) {
			throw new OperationException(body);
		}
		return responseToEntity(body, objClass, getRegion());
	}
	/* ************************************ create ****************************** */
	protected T _create(String url, Object data) throws OperationException {
		return _post(url, data);
	}
	/* *************************************** post ************************ */
	protected T _post(String url, Object data)  throws OperationException {
		return _post(url, data, objClass);
	}
	protected <K extends AbstractEntity> K _post(String url, Object data, Class<K> cl) throws OperationException {
		Response response = null;
		url = getEndpoint() + url;
		try {
			response = client.doPost(url, data);
		} catch (Exception ex) {
			throw new OperationException(ex.getMessage());
		}
		int code = response.getCode();
		String body = response.getBody();
		if (notIn(code, 200, 300)) {
			throw new OperationException(body);
		}
		return responseToEntity(body, cl, getRegion());
	}
}
