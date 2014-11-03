package bupt.openstack.glance.model;
import java.util.List;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.Link;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class Image extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -73901266800245673L;
	@bupt.openstack.common.annotation.Property
	private String status;
	@bupt.openstack.common.annotation.Property
	private boolean deleted;
	@bupt.openstack.common.annotation.Property("container_format")
	private String containerFormat;
	@bupt.openstack.common.annotation.Property("created_at")
	private String createdAt;
	@bupt.openstack.common.annotation.Property("disk_format")
	private String diskFormat;
	@bupt.openstack.common.annotation.Property("update_at")
	private String updateAt;
	@bupt.openstack.common.annotation.Property("min_disk")
	private int minDisk;
	@bupt.openstack.common.annotation.Property
	private boolean isProtected;
	@bupt.openstack.common.annotation.Property("min_ram")
	private int minRam;
	@bupt.openstack.common.annotation.Property
	private String checksum;
	@bupt.openstack.common.annotation.Property
	private String owner;
	@bupt.openstack.common.annotation.Property("is_public")
	private boolean isPublic;
	@bupt.openstack.common.annotation.Property("deleted_at")
	private String deletedAt;
	@bupt.openstack.common.annotation.Property
	private ImageProperty properties;
	@bupt.openstack.common.annotation.Property
	private List<Link> links;
	@bupt.openstack.common.annotation.Property
	private long size;
	@bupt.openstack.common.annotation.Property
	private String type;
	@bupt.openstack.common.annotation.Property
	private int progress;
	
	public Image() {
		super();
	}
	public Image(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public Image(JSONObject image) {
		super(image);
	}
	public Image(String s) {
		this(new JSONObject(s));
	}
	
	public String getContainerFormat() {
		return containerFormat;
	}
	public void setContainerFormat(String containerFormat) {
		this.containerFormat = containerFormat;
	}
	public String getDiskFormat() {
		return diskFormat;
	}
	public void setDiskFormat(String diskFormat) {
		this.diskFormat = diskFormat;
	}
	public int getMinDisk() {
		return minDisk;
	}
	public void setMinDisk(int minDisk) {
		this.minDisk = minDisk;
	}
	public boolean isProtected() {
		return isProtected;
	}
	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}
	public int getMinRam() {
		return minRam;
	}
	public void setMinRam(int minRam) {
		this.minRam = minRam;
	}
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	public String getStatus() {
		return status;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public String getChecksum() {
		return checksum;
	}
	public String getOwner() {
		return owner;
	}
	public String getDeletedAt() {
		return deletedAt;
	}
	public ImageProperty getProperties() {
		return properties;
	}
	public List<Link> getLinks() {
		return links;
	}
	public long getSize() {
		return size;
	}
	@Deprecated
	public void setSize(long size) {
		this.size = size;
	}
	@Deprecated
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getType() {
		return type;
	}
	public int getProgress() {
		return progress;
	}
	@Override
	public boolean isValid() {
		check();
		return true;
	}

	public void check() {
		if (containerFormat == null || containerFormat.length() < 1) {
			setContainerFormat("bare");
		}
		if (diskFormat == null || diskFormat.length() < 1) {
			setDiskFormat("qcow2");
		}
		if (name == null || name.length() < 1) {
			name = "unnaming";
		}
	}
	@Entity("property")
	public static class ImageProperty extends AbstractEntity {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4016140732525467538L;
		@Property("instance_uuid")
		private String instanceId;
		@Property("image_state")
		private String state;
		@Property("image_type")
		private String type;
		@Property("instance_type_flavorid")
		private String flavorRef;
		@Property
		private String description;
		@Property("ramdisk_id")
		private String ramDiskId;
		@Property("kernel_id")
		private String kernelId;
		@Property("base_image_ref")
		private String ImageRef;
		@Property("user_id")
		private String userId;
		@Property("owner_id")
		private String tenantId;
		@Property("os_type")
		private String osType;
		public ImageProperty(JSONObject json) {
			super(json);
		}
		public ImageProperty(Object o) {
			this(new JSONObject(o.toString()));
		}
		public ImageProperty(String s) {
			this(new JSONObject(s));
		}
		
		public String getInstanceId() {
			return instanceId;
		}
		public String getState() {
			return state;
		}
		public String getType() {
			return type;
		}
		public String getFlavorRef() {
			return flavorRef;
		}
		public String getDescription() {
			return description;
		}
		public String getRamDiskId() {
			return ramDiskId;
		}
		public String getKernelId() {
			return kernelId;
		}
		public String getImageRef() {
			return ImageRef;
		}
		public String getUserId() {
			return userId;
		}
		public String getTenantId() {
			return tenantId;
		}
		public String getOsType() {
			return osType;
		}
		@Override
		public boolean isValid() {
			return true;
		}
	}
}
