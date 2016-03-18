package bupt.openstack.nova.api;

import java.util.List;

import bupt.openstack.common.OperationException;
import bupt.openstack.nova.model.KeyPair;

public interface KeyPairManager {
	KeyPair create(String name) throws OperationException;
	KeyPair create(String name, String publicKey) throws OperationException;
	void delete(String name) throws OperationException;
	List<KeyPair> list() throws OperationException;
	KeyPair get(String name) throws OperationException;
	
}
