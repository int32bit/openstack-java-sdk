package bupt.openstack.common.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HTTPHeader implements Header {
	private Map<String, Object> entry = new HashMap<>();
	@Override
	public Object put(String key, Object value, boolean override) {
		Object oldValue = entry.get(key);
		if (oldValue != null) {
			if (override) {
				return entry.put(key, value);
			} else {
				return value;
			}
		}
		return entry.put(key, value);
	}
	@Override
	public Object put(String key, Object value) {
		return put(key, value, true);
	}
	@Override
	public void putAll(Map<String, ? extends Object> mapper) {
		entry.putAll(mapper);
	}

	@Override
	public Object get(String key) {
		return entry.get(key);
	}
	@Override
	public Object get(String key,Object defaultValue) {
		Object value = entry.get(key);
		return value == null ? defaultValue : value;
	}
	@Override
	public void clear() {
		entry.clear();
	}
	@Override
	public boolean hasKey(String key) {
		return entry.containsKey(key);
	}
	@Override
	public Set<String> keySet() {
		return entry.keySet();
	}
}
