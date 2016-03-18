package bupt.openstack.cinder.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity("limits")
public class Limit extends AbstractEntity {

	/**
	 * 
	 */
	@Property
	private int maxTotalBackupGigabytes;
	@Property
	private int maxTotalBackups;
	@Property
	private int maxTotalSnapshots;
	@Property
	private int maxTotalVolumeGigabytes;
	@Property
	private int maxTotalVolumes;
	@Property
	private int totalBackupGigabytesUsed;
	@Property
	private int totalBackupsUsed;
	@Property
	private int totalGigabytesUsed;
	@Property
	private int totalSnapshotsUsed;
	@Property
	private int totalVolumesUsed;
	private static final long serialVersionUID = -3639348997871487380L;
	public Limit(JSONObject entity) {
		super(entity.getJSONObject("absolute"));
	}

	public Limit(Object obj) {
		this(new JSONObject(obj.toString()));
	}

	public Limit(String s) {
		super(new JSONObject(s));
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public int getMaxTotalBackupGigabytes() {
		return maxTotalBackupGigabytes;
	}

	public int getMaxTotalBackups() {
		return maxTotalBackups;
	}

	public int getMaxTotalSnapshots() {
		return maxTotalSnapshots;
	}

	public int getMaxTotalVolumeGigabytes() {
		return maxTotalVolumeGigabytes;
	}

	public int getMaxTotalVolumes() {
		return maxTotalVolumes;
	}

	public int getTotalBackupGigabytesUsed() {
		return totalBackupGigabytesUsed;
	}

	public int getTotalBackupsUsed() {
		return totalBackupsUsed;
	}

	public int getTotalGigabytesUsed() {
		return totalGigabytesUsed;
	}

	public int getTotalSnapshotsUsed() {
		return totalSnapshotsUsed;
	}

	public int getTotalVolumesUsed() {
		return totalVolumesUsed;
	}
}
