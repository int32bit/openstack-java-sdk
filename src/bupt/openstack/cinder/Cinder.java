package bupt.openstack.cinder;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.cinder.api.LimitManager;
import bupt.openstack.cinder.api.ServiceManager;
import bupt.openstack.cinder.api.VolumeManager;
import bupt.openstack.cinder.api.VolumeTypeManager;
import bupt.openstack.cinder.api.v1.Limits;
import bupt.openstack.cinder.api.v1.Services;
import bupt.openstack.cinder.api.v1.SnapshotManager;
import bupt.openstack.cinder.api.v1.Types;
import bupt.openstack.cinder.api.v1.Volumes;
import bupt.openstack.cinder.model.Snapshots;

public class Cinder {
	public final VolumeManager volumes;
	public final VolumeTypeManager types;
	public final ServiceManager services;
	public final SnapshotManager snapshots;
	public final LimitManager limits;
	public Cinder(Authenticated credentical) {
		volumes = new Volumes(credentical);
		types = new Types(credentical);
		services = new Services(credentical);
		snapshots = new Snapshots(credentical);
		limits = new Limits(credentical);
	}
}
