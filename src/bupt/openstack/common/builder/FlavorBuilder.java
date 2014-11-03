package bupt.openstack.common.builder;

import bupt.openstack.nova.model.Flavor;

public class FlavorBuilder extends EntityBuilder implements FlavorBuilderInterface {
	private String id;
	private String name;
	private int ram;
	private int vcpus;
	private int swap;
	private int disk;
	private int ephemeral;
	private boolean isPublic = true;
	private double rxtx_factor = 1.0;
	public static final int MAX_RAM = 1024 * 32;
	public static final int MIN_RAM = 128;
	public static final int MAX_VCPUS = 32;
	public static final int MIN_VCPUS = 1;
	public static final int MAX_SWAP = 32;
	public static final int MIN_SWAP = 0;
	public static final int MAX_DISK = 512;
	public static final int MIN_DISK = 1;
	public static final int MAX_EPHEMERAL = 512;
	public static final int MIN_EPHEMERAL = 0;
	@Override
	public FlavorBuilder id(String id) {
		this.id = id;
		return this;
	}
	@Override
	public FlavorBuilder name(String name) {
		this.name = name;
		return this;
	}
	@Override
	public FlavorBuilder ram(int ram) {
		this.ram = getFitPow2(ram);
		return this;
	}
	@Override
	public FlavorBuilder vcpus(int vcpus) {
		this.vcpus = getFitPow2(vcpus);
		return this;
	}
	@Override
	public FlavorBuilder swap(int swap) {
		this.swap = getFitPow2(swap);
		return this;
	}
	@Override
	public FlavorBuilder disk(int disk) {
		this.disk = disk;
		return this;
	}
	@Override
	public FlavorBuilder isPublic(boolean isPublic) {
		this.isPublic = isPublic;
		return this;
	}
	@Override
	public FlavorBuilder rxtxFactor(double factor) {
		this.rxtx_factor = factor;
		return this;
	}
	@Override
	public Flavor build() {
		Flavor flavor = new Flavor();
		if (name == null || name.length() == 0) {
			if (id != null && id.length() > 0) {
				flavor.setName(id);
			} else {
				flavor.setName("flavor" + System.currentTimeMillis());
			}
		}
		flavor.setEphemeral(getFitValue(ephemeral, MAX_EPHEMERAL, MIN_EPHEMERAL));
		flavor.setRam(getFitValue(ram, MAX_RAM, MIN_RAM));
		flavor.setVcpus(getFitValue(vcpus, MAX_VCPUS, MIN_VCPUS));
		flavor.setSwap(getFitValue(swap, MAX_SWAP, MIN_SWAP));
		flavor.setDisk(getFitValue(disk, MAX_DISK, MIN_DISK));
		flavor.setPublic(isPublic);
		flavor.setRxtxFactor(getFitValue(rxtx_factor, 10.0d, 1.0d));
		return flavor;
	}
	public static void main(String[] args) {
		FlavorBuilder builder = new FlavorBuilder();
		Flavor flavor = builder.build();
		System.out.println(flavor.toString(4));
	}
}
