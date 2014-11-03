package bupt.openstack.nova.api.v2;

import java.util.List;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.OperationException;
import bupt.openstack.nova.api.KeyPairManager;
import bupt.openstack.nova.model.KeyPair;

public class KeyPairs extends AbstractManager<KeyPair> implements KeyPairManager {

	public KeyPairs(Authenticated credentical) {
		super(credentical, KeyPair.class);
	}
	@Override
	public void delete(String name) throws OperationException {
		_delete("/os-keypairs/" + name);
	}

	@Override
	public KeyPair get(String name) throws OperationException {
		return _get("/os-keypair/" + name);
	}

	@Override
	public List<KeyPair> list() throws OperationException {
		return _list("/os-keypairs");
	}

	@Override
	public KeyPair create(String name) throws OperationException {
		KeyPair keypair = new KeyPair();
		keypair.setName(name);
		return _create("/os-keypairs", keypair);
	}

	@Override
	public KeyPair create(String name, String publicKey)
			throws OperationException {
		KeyPair keypair = new KeyPair();
		keypair.setName(name);
		keypair.setPublicKey(publicKey);
		return _create("/os-keypairs", keypair);
	}
}
