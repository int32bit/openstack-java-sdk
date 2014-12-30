package bupt.openstack.keystone.model;

public enum CatalogType {
	compute,image,identity, cloudformation, volume, volumev2, orchestration,network;
	public static CatalogType of(String type) {
		return CatalogType.valueOf(type);
	}
}
