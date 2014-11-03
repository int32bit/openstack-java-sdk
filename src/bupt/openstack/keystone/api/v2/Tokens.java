package bupt.openstack.keystone.api.v2;

import java.util.Set;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.authentication.AuthenticationException;
import bupt.openstack.common.OperationException;
import bupt.openstack.common.conf.Configure;
import bupt.openstack.keystone.api.TokenManager;
import bupt.openstack.keystone.model.Access;
import bupt.openstack.keystone.model.CatalogType;
import bupt.openstack.keystone.model.Secret;
import bupt.openstack.keystone.model.Tenant;
import bupt.openstack.keystone.model.User;

public class Tokens extends AbstractManager<Access> implements TokenManager{
	public Tokens() {
		/** 还没有认证，无法获取证书，所以创建了一个伪证书 **/
		super(new FakeCredentical(), Access.class);
	}
	public Tokens(Authenticated credentical) {
		super(credentical, Access.class);
	}
	@Override
	public Access authenticate(Secret secret) throws OperationException{
		if (!secret.isValid()) {
			throw new AuthenticationException("The secret is invalid!");
		}
		return super._create("/tokens", secret);
	}
	@Override
	public void destroy(String id) throws OperationException {
		/* FIXME 每次请求，无论成功还是失败，都会抛出异常，直接忽略 */
		try {
			super._delete("/tokens/" + id);
		} catch (Exception ignore) {
			
		}
	}
	/** 返回认证url，由配置文件指定 **/
	@Override
	public String getEndpoint() {
		String value = Configure.getConfigure().getString("AuthURL");
		if (value == null) {
			throw new RuntimeException("Failed to get auth url!");
		}
		return value;
	}
	/** 伪证书，请勿使用！！！  **/
	private static class FakeCredentical implements Authenticated {
		@Override
		public String getTokenId() {
			return null;
		}

		@Override
		public User getUser() {
			return null;
		}

		@Override
		public Tenant getTenant() {
			return null;
		}

		@Override
		public String getEndpoint(CatalogType type) {
			return null;
		}

		@Override
		public String getWorkRegion() {
			return null;
		}

		@Override
		public void setWorkRegion(String region) {
			
		}

		@Override
		public Set<String> getRegions() {
			return null;
		}
		
	}
}
