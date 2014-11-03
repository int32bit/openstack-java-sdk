package bupt.openstack.nova.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.nova.api.FlavorManager;
import bupt.openstack.nova.model.Flavor;
public class Flavors extends AbstractManager<Flavor> implements FlavorManager{
	private final String PREFIX = "/flavors";
	public Flavors(Authenticated credentical) {
		super(credentical, Flavor.class);
	}
	/**
	 * Get a list of all flavors
	 * @return A List , which holds flavors
	 * @throws OperationException
	 */
	@Override
	public List<Flavor> list() throws OperationException {
		return _list(PREFIX + "/detail");
	}
	/**
	 * Get a specific Flavor.
	 * @param id The ID of Flavor to get. 
	 * @return Flavor
	 * @throws OperationException
	 */
	@Override
	public Flavor get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}
	/**
	 * Delete a specific Flavor.
	 * @param id The ID of Flavor to delete.
	 * @throws OperationException
	 */
	@Override
	public void delete(String id) throws OperationException {
		_delete("/flavors/" + id);
	}
	/**
	 * create a new flavor for a tenant
	 * @param flavor The flavor to create
	 * @return The new flavor
	 * @throws OperationException
	 */
	@Override
	public Flavor create(Flavor flavor) throws OperationException {
		return _create("/flavors", flavor);
	}
	/**
	 * update a flavor
	 * @param flavor the flavor to update, with a some new property except id.
	 * @return The updated flavor
	 * @throws OperationException
	 */
	@Override
	public Flavor update(Flavor flavor) throws OperationException {
		throw new java.lang.UnsupportedOperationException("This version api not support update flavor!");
	}
}
