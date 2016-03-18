package bupt.openstack.common.request;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class HTTPRequest extends AbstractHTTPRequest implements AutoCloseable {
	private static long requestTimes = 0;
	private CloseableHttpClient proxy;
	private static Logger logger = LogManager.getFormatterLogger(HTTPRequest.class);
	public HTTPRequest() {
		proxy = HttpClients.createDefault();
	}
	@Override
	public void close() throws Exception {
		destroy();
		proxy.close();
	}
	public static long getRequestTimes() {
		return requestTimes;
	}
	
	public Response request(HttpUriRequest httpRequest) throws IOException {
		++requestTimes;
		String format = "%d %s:%s";
		logger.debug(format, requestTimes, httpRequest.getMethod(), httpRequest.getURI());
		setHeader(httpRequest);
		CloseableHttpResponse resp = proxy.execute(httpRequest);
		Response response = processResponse(resp);
		resp.close();
		return response;
	}
	public Response request(HttpUriRequest httpRequest, Object data)
			throws IOException {
		++requestTimes;
		String format = "%d %s:%s data:%s";
		logger.debug(format, requestTimes, httpRequest.getMethod(), httpRequest.getURI(), data);
		setHeader(httpRequest);
		if (httpRequest instanceof HttpEntityEnclosingRequest) {
			HttpEntity entity = null;
			if (data != null) {
				if (data instanceof File) {
					entity = new FileEntity((File) data);
				} else if(data instanceof byte[]) {
					entity = new ByteArrayEntity((byte[])data);
				} else if(data instanceof InputStream) {
					entity = new InputStreamEntity((InputStream)data);
				} else {
					try {
						entity = new StringEntity(data.toString());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			((HttpEntityEnclosingRequest) httpRequest).setEntity(entity);
		}
		CloseableHttpResponse resp = proxy.execute(httpRequest);
		Response response = processResponse(resp);
		resp.close();
		return response;
	}

	public void clean() {
		clearHeader();
	}
	private void setHeader(HttpRequest request) {
		for (String key : getHeader().keySet()) {
			request.setHeader(key, getHeader().get(key).toString());
		}
	}
	private boolean canProcess(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		Objects.requireNonNull(entity);
		String contentType = entity.getContentType().getValue();
		if (contentType.indexOf("application/octet-stream") >= 0) {
			return false;
		}
		return true;
	}
	private Response processResponse(HttpResponse res) {
		HTTPResponse response = new HTTPResponse();
		HttpEntity entity = res.getEntity();
		/* 有些请求直接把内容放到头部，比如glance */
		if (entity == null) {
			JSONObject json = new JSONObject();
			for (org.apache.http.Header header : res.getAllHeaders()) {
				json.put(header.getName(), header.getValue());
			}
			response.setBody(json.toString());
			return response;
		}
		StatusLine status = res.getStatusLine();
		response.setCode(status.getStatusCode());
		response.setStatus(status.getReasonPhrase());
		if (!canProcess(res)) {
			try {
				response.setInputStream(entity.getContent());
			} catch(Exception e) {
				e.printStackTrace();
			}
			return response;
		}
		
		/*if (entity == null) {
			JSONObject json = new JSONObject();
			for (org.apache.http.Header header : res.getAllHeaders()) {
				json.put(header.getName(), header.getValue());
			}
			response.setBody(json.toString());
			return response;
		}*/
		try {
			response.setBody(EntityUtils.toString(entity));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		} finally {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@Override
	protected Response get(String url) throws IOException {
		return request(new HttpGet(url));
	}
	protected Response post(String url) throws IOException {
		return request(new HttpHead(url));
	}

	@Override
	protected Response delete(String url) throws IOException {
		return request(new HttpDelete(url));
	}
	@Override
	protected Response post(String url, Object data) throws IOException {
		return request(new HttpPost(url), data);
	}


	@Override
	public Response put(String url, Object data) throws IOException {
		return request(new HttpPut(url), data);
	}
	@Override
	public Response head(String url) throws IOException {
		return request(new HttpHead(url));
	}
}
