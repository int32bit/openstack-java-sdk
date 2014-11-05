package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.Image;

public interface ImageManager {
	Image get(String id) throws OperationException;
	List<Image> list() throws OperationException;
	void delete(String id) throws OperationException;
	
}
