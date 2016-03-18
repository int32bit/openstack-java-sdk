package bupt.openstack.cinder.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.Link;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class Volume extends AbstractEntity {

	private static final long serialVersionUID = -4930931167282861183L;
	@Property
	private String status;
	@Property
	private List<Link> links;
	@Property
	private List<Attachment> attachments;
	@Property("availability_zone")
	private String availabilityZone;
	@Property("os-vol-host-attr:host")
	private String host;
	@Property("source_volid")
	private String sourceVolid;
	@Property("snapshot_id")
	private String snapshotId;
	@Property("display_description")
	private String description;
	@Property("user_id")
	private String userId;
	@Property
	private boolean bootable;
	@Property("created_at")
	private String createdAt;
	@Property("volume_type")
	private String volumeType;
	@Property("os-vol-tenant-attr:tenant_id")
	private String tenantId;
	@Property
	private int size;
	@Property("os-vol-mig-status-attr:migstat")
	private String migstat;
	@Property
	private Metadata metadata;
	@Property("display_name")
	private String displayName;
	@Property("os-vol-mig-status-attr:name_id")
	private String nameId;
	private String userName;
	private String tenantName;
	public Volume() {
		super();
		this.bootable = false;
	}

	public Volume(JSONObject volume) {
		super(volume);
		if (getName() == null) {
			this.name = volume.optString("display_name", null);
		}
		if (getDescription() == null)
			this.description = volume.optString("display_description", null);
		JSONArray attachments = volume.getJSONArray("attachments");
		if (attachments != null && attachments.length() > 0) {
			//JSONArray attachments = volume.getJSONArray("attachments");
			this.attachments = new ArrayList<>();
			for (int i = 0; i < attachments.length(); ++i) {
				this.attachments.add(new Attachment(attachments.get(i)));
			}
		}
	}
	public Volume(Object o) {
		this(new JSONObject(o.toString()));
	}
	public Volume(String s) {
		this(new JSONObject(s));
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getStatus() {
		return status;
	}

	public List<Link> getLinks() {
		return links;
	}

	public List<Attachment> getAttachment() {
		return attachments;
	}

	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public String getHost() {
		return host;
	}

	public String getSourceVolid() {
		return sourceVolid;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setUsername(String name) {
		this.userName = name;
	}
	public void setTenantName(String name) {
		this.tenantName = name;
	}
	public boolean isBootable() {
		return bootable;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getMigstat() {
		return migstat;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public String getNameId() {
		return nameId;
	}
	@Override
	public void setName(String name) {
		this.name = name;
		this.displayName = name;
	}
	public void setDisplayName(String name) {
		this.name = name;
		this.displayName = name;
	}
	@Override
	public boolean isValid() {
		return true;
	}
	@Entity
	public static class Attachment extends AbstractEntity {
		/**
	 * 
	 */
		private static final long serialVersionUID = 8324437094460259420L;
		@Property("host_name")
		private String hostname;
		@Property
		private String device;
		@Property("server_id")
		private String instanceId;
		@Property("volume_id")
		private String volumeId;
		private String instanceName;

		public Attachment() {
			super();
		}

		public Attachment(JSONObject entity) {
			super(entity);
		}
		public Attachment(String s) {
			super(s);
		}
		public Attachment(Object obj) {
			this(new JSONObject(obj.toString()));
		}
		
		public String getHostname() {
			return hostname;
		}

		public String getDevice() {
			return device;
		}

		public String getInstanceId() {
			return instanceId;
		}
		public String getInstanceName() {
			return this.instanceName;
		}
		public void setInstanceName(String name) {
			this.instanceName = name;
		}

		public String getVolumeId() {
			return volumeId;
		}

		@Override
		public boolean isValid() {
			return true;
		}
	}
	@Entity
	public static class Metadata extends AbstractEntity {
		/**
	 * 
	 */
		private static final long serialVersionUID = 3692033215545602819L;
		@Property("attached_mode")
		String mode;
		@Property
		boolean readonly;

		public Metadata(JSONObject meta) {
			super(meta);
		}

		public Metadata(Object obj) {
			this(new JSONObject(obj.toString()));
		}
		public Metadata(String s) {
			this(new JSONObject(s));
		}
		public String getMode() {
			return mode;
		}

		public boolean isReadonly() {
			return readonly;
		}

		@Override
		public boolean isValid() {
			return true;
		}
	}
}
