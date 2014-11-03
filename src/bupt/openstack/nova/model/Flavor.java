package bupt.openstack.nova.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.Link;
import bupt.openstack.common.model.AbstractEntity;
@Entity("flavor")
public class Flavor extends AbstractEntity {
	private static final long serialVersionUID = -4771822402986000365L;
	@Property
	private int ram;
	@Property
	private int vcpus;
	@Property
	private int swap;
	@Property
	private int disk;
	@Property("OS-FLV-EXT-DATA:ephemeral")
	private int ephemeral;
	@Property("os-flavor-access:is_public")
	private boolean isPublic = true;
	@Property("rxtx_factor")
	private double rxtxFactor;
	@Property("OS-FLV-DISABLED:disabled")
	private boolean disabled = false;
	@Property
	private List<Link> links;

	public Flavor(JSONObject flavor) {
		super(flavor);
	}

	public Flavor() {
		super();
	}
	public Flavor(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Flavor(String s) {
		this(new JSONObject(s));
	}
	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getVcpus() {
		return vcpus;
	}

	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}

	public int getSwap() {
		return swap;
	}

	public void setSwap(int swap) {
		this.swap = swap;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getEphemeral() {
		return ephemeral;
	}

	public void setEphemeral(int ephemeral) {
		this.ephemeral = ephemeral;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public double getRxtxFactor() {
		return rxtxFactor;
	}

	public void setRxtxFactor(double rxtxFactor) {
		this.rxtxFactor = rxtxFactor;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public List<Link> getLinks() {
		if (links == null) {
			links = new ArrayList<>();
		}
		return Collections.unmodifiableList(links);
	}

	@Override
	public boolean isValid() {
		if ((this.name == null) || (this.name.length() < 1))
			return false;
		if ((this.ram < 128) || (this.disk < 1) || (this.vcpus < 1)) {
			return false;
		}
		if (rxtxFactor < 1.0) {
			rxtxFactor = 1.0;
		}
		if ((this.ephemeral < 0))
			return false;
		return true;
	}

	@Override
	public String toString() {
		JSONObject flavor = new JSONObject();
		if (id == null || id.length() < 1) {
			flavor.put("id", JSONObject.NULL);
		} else {
			flavor.put("id", id);
		}
		flavor.put("vcpus", getVcpus());
		flavor.put("disk", getDisk());
		flavor.put("name", getName());
		flavor.put("os-flavor-access:is_public", isPublic());
		flavor.put("rxtx_factor", getRxtxFactor());
		flavor.put("OS-FLV-EXT-DATA:ephemeral", this.getEphemeral());
		flavor.put("ram", getRam());
		flavor.put("swap", getSwap());
		JSONObject t = new JSONObject();
		t.put("flavor", flavor);
		return t.toString();
	}
}