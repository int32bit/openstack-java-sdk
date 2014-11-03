package bupt.openstack.common.builder;

public interface FlavorBuilderInterface extends EntityBuilderInterface {
	EntityBuilderInterface ram(int ram);
	EntityBuilderInterface vcpus(int vcpus);
	EntityBuilderInterface swap(int swap);
	EntityBuilderInterface disk(int disk);
	EntityBuilderInterface isPublic(boolean isPublic);
	EntityBuilderInterface rxtxFactor(double factor);
}
