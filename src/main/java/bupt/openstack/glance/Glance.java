package bupt.openstack.glance;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.Client;
import bupt.openstack.glance.api.ImageManager;
import bupt.openstack.glance.api.v2.Images;

public class Glance extends Client {
	public final ImageManager images;
	public Glance(Authenticated credentical) {
		super(credentical);
		this.images = new Images(credentical);
	}
}
