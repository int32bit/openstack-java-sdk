package bupt.openstack.common.request;



public interface Request {
	Response doGet(String url) throws Exception;
	Response doGet(Header header, String url) throws Exception;
	Response doPost(String url, Object data) throws Exception;
	Response doPost(Header header, String url, Object data) throws Exception;
	Response doPut(String url, Object data) throws Exception;
	Response doPut(Header header, String url, Object data) throws Exception;
	Response doDelete(String url) throws Exception;
	Response doDelete(Header header, String url) throws Exception;
	Response doHead(String url) throws Exception;
	Response doHead(Header header, String url) throws Exception;
}
