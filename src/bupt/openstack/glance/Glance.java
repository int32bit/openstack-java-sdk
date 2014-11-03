package bupt.openstack.glance;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.glance.api.ImageManager;
import bupt.openstack.glance.api.v2.Images;

public class Glance {
	public final ImageManager images;
	public Glance(Authenticated credentical) {
		this.images = new Images(credentical);
	}
}
