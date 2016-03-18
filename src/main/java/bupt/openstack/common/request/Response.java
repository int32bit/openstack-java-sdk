package bupt.openstack.common.request;

import java.io.InputStream;

public interface Response {
	String getBody();
	byte[] getBytes();
	InputStream getInputStream();
	int getCode();
	boolean isSuccess();
}
