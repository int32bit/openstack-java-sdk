package bupt.openstack.glance.api;
import java.io.InputStream;
import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.glance.model.Image;

public interface ImageManager {
	List<Image> list() throws OperationException;
	Image get(String id) throws OperationException;
	void delete(String id) throws OperationException;
	Image create(Image image, InputStream data) throws OperationException;
	InputStream download(String id) throws OperationException;
}
