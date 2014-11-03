package bupt.openstack.nova.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("availabilityZoneInfo")
public class AvailableZone extends AbstractEntity {
	/**
	 * 
	 */
	@Property("hosts")
	private List<String>hosts = new ArrayList<>();
	@Property
	private boolean available;
	private static final long serialVersionUID = 1L;
	@Override
	public boolean isValid() {
		return true;
	}
	public AvailableZone(JSONObject zone) {
		this.available = zone.getJSONObject("zoneState").getBoolean("available");
		this.name = zone.getString("zoneName");
		JSONObject jsonHosts = zone.getJSONObject("hosts");
		for (Object key : jsonHosts.keySet()) {
			hosts.add((String)key);
		}
		this.id = "Zone@" + name;
	}
	public List<String> getHosts() {
		return Collections.unmodifiableList(hosts);
	}
	public boolean isAvailable() {
		return available;
	}
	

}
