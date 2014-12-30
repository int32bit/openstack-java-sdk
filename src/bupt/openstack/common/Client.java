package bupt.openstack.common;

import bupt.openstack.authentication.Authenticated;

public abstract class Client {
	protected Authenticated credentical;
	public Client(Authenticated credentical) {
		this.credentical = credentical;
	}
	public void setRegion(String region) {
		credentical.setWorkRegion(region);
	}
	public Authenticated getCredentical () {
		return credentical;
	}
}
