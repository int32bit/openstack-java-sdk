package bupt.openstack.common.request;

import java.io.InputStream;
import java.util.Objects;



public class HTTPResponse implements Response{
	private String body = "";
	private int code = -1;
	private String status;
	private byte[] data;
	private InputStream inputStream = null;
	public HTTPResponse() {
		
	}
	@Override
	public String getBody() {
		if (body == null) {
			Objects.requireNonNull(data);
			return data.toString();
		}
		return body;
	}
	@Override
	public int getCode() {
		return this.code;
	}
	public void setBody(String body) {
		this.body = body;
		this.data = body.getBytes();
	}
	public void setBody(byte[] data) {
		this.data = data;
		this.body = data.toString();
	}
	@Override
	public byte[] getBytes() {
		if (data == null) {
			Objects.requireNonNull(body, "Failed to get content, you may call getInputStream()");
			return body.getBytes();
		}
		return data;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public boolean isSuccess() {
		if (code == -1)
			return true;
		return bupt.openstack.common.tools.Math.in(code, 200, 300);
	}
	public void setInputStream(InputStream in) {
		this.inputStream = in;
	}
	@Override
	public InputStream getInputStream() {
		Objects.requireNonNull(inputStream, "Faild to get stream from server, you may call getBody() or getBytes()");
		return inputStream;
	}
}
