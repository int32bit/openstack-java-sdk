package bupt.openstack.common.request;

import java.io.IOException;
import java.util.Map;

public abstract class AbstractHTTPRequest implements Request{
	private Header header = new HTTPHeader();
	protected void init(Object[] args) {
		
	}
	protected void before() {
		
	}
	protected void before(Header header) {
		if (header != null) {
			this.header = header;
		}
	}
	protected void after() {
		
	}
	protected void after(Header header) {
		
	}
	protected void destroy() {
		
	}
	protected abstract Response get(String url) throws IOException;
	protected abstract Response delete(String url) throws IOException;
	protected abstract Response post(String url, Object data) throws IOException;
	protected abstract Response put(String url, Object data) throws IOException;
	protected abstract Response head(String url) throws IOException;
	@Override
	public Response doGet(String url) throws Exception {
		before();
		Response response = get(url);
		after();
		return response;
	}
	@Override
	public Response doGet(Header header, String url) throws IOException {
		before(header);
		Response response = get(url);
		after(header);
		return response;
	}
	@Override
	public Response doPost(String url, Object data) throws IOException {
		before();
		Response response = post(url, data);
		after();
		return response;
	}
	@Override
	public Response doPost(Header header, String url, Object data) throws IOException {
		before(header);
		Response response = post(url, data);
		after(header);
		return response;
	}
	@Override
	public Response doPut(String url, Object data) throws IOException {
		before();
		Response response = put(url, data);
		after();
		return response;
	}
	@Override
	public Response doPut(Header header, String url, Object data) throws IOException {
		before(header);
		Response response = put(url, data);
		after(header);
		return response;
	}
	@Override
	public Response doDelete(String url) throws IOException {
		before();
		Response response = delete(url);
		after();
		return response;
	}
	@Override
	public Response doDelete(Header header, String url) throws IOException {
		before(header);
		Response response = delete(url);
		after(header);
		return response;
	}
	@Override
	public Response doHead(Header header, String url) throws IOException {
		before(header);
		Response response = head(url);
		after(header);
		return response;
	}
	@Override
	public Response doHead(String url) throws IOException {
		before();
		Response response = head(url);
		after();
		return response;
	}
	@Override
	public void finalize() {
		destroy();
	}
	public Header getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header.putAll(header);
	}

	public void setHeader(String key, String value) {
		header.put(key, value);
	}
	public void setHeader(Header header) {
		this.header = header;
	}
	public void clearHeader() {
		header.clear();
	}
}
