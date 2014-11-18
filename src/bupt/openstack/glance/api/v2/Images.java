package bupt.openstack.glance.api.v2;

import java.io.InputStream;
import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.request.HTTPHeader;
import bupt.openstack.common.request.Header;
import bupt.openstack.common.request.HttpMethod;
import bupt.openstack.common.request.Response;
import bupt.openstack.common.tools.JSONConverter;
import bupt.openstack.common.tools.StringUtils;
import bupt.openstack.glance.model.Image;

public class Images extends AbstractManager<Image> {
	public Images(Authenticated credentical) {
		super(credentical, Image.class);
	}

	@Override
	public List<Image> list() throws OperationException {
		return _list("/v2/images");
	}

	@Override
	public Image get(String id) throws OperationException {
		return _get("/v2/images/" + id, "");
	}

	@Override
	public void delete(String id) throws OperationException {
		_delete("/v2/images/" + id);
	}
	@Override
	public Image create(Image image, InputStream data) throws OperationException {
		Header header = new HTTPHeader();
		if (StringUtils.isNotBlank(image.getName())) {
			header.put("x-image-meta-name", image.getName());
		} else {
			throw new OperationException("the name of image is required!");
		}
		if (StringUtils.isNotBlank(image.getContainerFormat())) {
			header.put("x-image-meta-container_format", image.getContainerFormat());
		} else {
			throw new OperationException("The container-format required!");
		}
		if (StringUtils.isNotBlank(image.getDiskFormat())) {
			header.put("x-image-meta-disk_format", image.getDiskFormat());
		} else {
			throw new OperationException("The disk-format required!");
		}
		if (image.isPublic()) {
			header.put("x-image-meta-is_public", "True");
		} else {
			header.put("x-image-meta-is_public", "False");
		}
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Content-Type", "application/octet-stream");
		header.put("X-Auth-Token", credentical.getTokenId());
		header.put("X-Auth-Project-Id", credentical.getTenant().getName());
		Response response = null;
		//FIXME use version v1, not v2! how to call v2 version ?
		try {
			response = super.request(header, "/v1/images", HttpMethod.POST, data);
		} catch(Exception e) {
			throw new OperationException(e);
		}
		if (response.isSuccess()) {
			return JSONConverter.responseToEntity(response.getBody(), Image.class, getRegion());
		} else {
			throw new OperationException("Failed to create image: %d:%s", response.getCode(),response.getBody());
		}
	}
	@Override
	public InputStream download(String id) throws OperationException {
		Header header = new HTTPHeader();
		header.put("Accept-Encoding", "gzip, deflate");
		header.put("Content-Type", "application/octet-stream");
		header.put("Accept", "*/*");
		header.put("X-Auth-Token", credentical.getTokenId());
		header.put("X-Auth-Project-Id", credentical.getTenant().getName());
		Response response = null;
		try {
			response = super.request(header, "/v2/images/" + id + "/file", HttpMethod.GET, null);
		} catch (Exception e) {
			throw new OperationException(e);
		}
		if (response.isSuccess()) {
			return response.getInputStream();
		} else {
			throw new OperationException("Failed to get image!");
		}
	}
}
