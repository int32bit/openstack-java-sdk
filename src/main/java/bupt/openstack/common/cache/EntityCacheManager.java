package bupt.openstack.common.cache;

import org.apache.jcs.JCS;
import org.apache.jcs.access.GroupCacheAccess;
import org.apache.jcs.access.exception.CacheException;

public enum EntityCacheManager implements GroupCache {
	INSTANCE;
	private GroupCacheAccess cache;
	private EntityCacheManager() {
		try {
			cache = JCS.getGroupAccess(getClass().getName());
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public Object get(Object id) {
		return cache.get(id);
	}

	@Override
	public Object put(Object id, Object obj) {
		Object old = cache.get(id);
		try {
			cache.put(id, obj);
		} catch (CacheException e) {
			throw new RuntimeException(e);
		}
		return old;
	}

	@Override
	public Object get(Object id, String group) {
		return cache.getFromGroup(id, group);
	}

	@Override
	public Object put(Object id, Object obj, String group) {
		Object old = cache.getFromGroup(id, group);
		try {
			cache.putInGroup(id, group, obj);
		} catch (CacheException e) {
			throw new RuntimeException(e);		
		}
		return old;
	}
	public static EntityCacheManager getInstance() {
		return EntityCacheManager.INSTANCE;
	}

}
