package bupt.openstack.glance.api.v2;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.Manager;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.glance.api.ImageManager;
import bupt.openstack.keystone.model.CatalogType;

public abstract class AbstractManager<T extends AbstractEntity> extends Manager<T> implements ImageManager {
	public AbstractManager(Authenticated credentical, Class<T> objClass) {
		super(credentical, objClass);
	}
	@Override
	public String getEndpoint() {
		return credentical.getEndpoint(CatalogType.image);
	}

}
