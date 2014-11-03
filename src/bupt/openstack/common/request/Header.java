package bupt.openstack.common.request;

import java.util.Map;
import java.util.Set;

public interface Header {
	Object put(String key, Object value, boolean override);
	Object put(String key, Object value);
	void putAll(Map<String, ? extends Object> mapper);
	Object get(String key);
	Object get(String key,Object defaultValue);
	boolean hasKey(String key);
	void clear();
	Set<String> keySet();
}
