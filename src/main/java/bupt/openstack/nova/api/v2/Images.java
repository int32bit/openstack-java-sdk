package bupt.openstack.nova.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Image;
import bupt.openstack.nova.api.ImageManager;

public class Images extends AbstractManager<Image> implements ImageManager {
	
	public Images(Authenticated credentical) {
		super(credentical, Image.class);
	}

	private static final String PREFIX = "/images";
	@Override
	public Image get(String id) throws OperationException {
		return _get(PREFIX + "/" + id);
	}

	@Override
	public List<Image> list() throws OperationException {
		return _list(PREFIX + "/detail");
	}

	@Override
	public void delete(String id) throws OperationException {
		_delete(PREFIX + "/" + id);

	}

}
