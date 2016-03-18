package bupt.openstack.common.model;

import org.json.JSONObject;
/**
 * 实现该接口，对象能够转化为JSONObject
 * @author fgp
 *
 */
public interface JSONAble {
	/**
	 * 把该对象转化成JSON表示
	 * @return 返回表示该对象JSONObject实例
	 */
	JSONObject toJSONObject();
}
