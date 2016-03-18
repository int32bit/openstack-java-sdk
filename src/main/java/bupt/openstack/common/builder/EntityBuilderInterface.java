package bupt.openstack.common.builder;

import bupt.openstack.common.model.AbstractEntity;

public interface EntityBuilderInterface {
	EntityBuilderInterface id(String id);
	EntityBuilderInterface name(String name);
	AbstractEntity build();
}
