package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.common.cache.EntityCacheManager;
import bupt.openstack.common.cache.GroupCache;
import bupt.openstack.nova.model.Flavor;

public class FlavorCachedManager implements FlavorManager {
	private FlavorManager flavorManager;
	private GroupCache cache = EntityCacheManager.getInstance();
	public FlavorCachedManager(FlavorManager flavorManager) {
		this.flavorManager = flavorManager;
	}
	@Override
	public List<Flavor> list() throws OperationException {
		return flavorManager.list();
	}
	@Override
	public Flavor get(String id) throws OperationException {
		return get(id, true);
	}
	public Flavor get(String id, boolean fromCache) throws OperationException {
		Flavor flavor = null;
		if (fromCache) {
			flavor = (Flavor)cache.get(id);
		}
		if (flavor == null) {
			flavor = flavorManager.get(id);
		}
		return flavor;
	}
	@Override
	public Flavor create(Flavor flavor) throws OperationException {
		return flavorManager.create(flavor);
	}
	@Override
	public void delete(String id) throws OperationException {
		flavorManager.delete(id);
	}
	@Override
	public Flavor update(Flavor flavor) throws OperationException {
		return flavorManager.update(flavor);
	}
}
