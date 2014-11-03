package bupt.openstack.keystone.model;

public enum CatalogType {
	compute,image,identity, cloudformation, volume, orchestration;
	public static CatalogType of(String type) {
		return CatalogType.valueOf(type);
	}
}
