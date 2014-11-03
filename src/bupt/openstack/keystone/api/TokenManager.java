package bupt.openstack.keystone.api;

import bupt.openstack.common.OperationException;
import bupt.openstack.keystone.model.Access;
import bupt.openstack.keystone.model.Secret;

public interface TokenManager {
	/**
	 * 认证接口，返回一个Access实例，Access封装了token
	 * @param secret 封装用户名、密码、token
	 * @return 返回Access实例
	 * @throws OperationException
	 */
	Access authenticate(Secret secret) throws OperationException;
	/**
	 * 销毁证书
	 * @param id 证书的id
	 * @throws OperationException
	 */
	void destroy(String id) throws OperationException;
}
