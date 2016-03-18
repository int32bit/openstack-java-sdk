package bupt.openstack.nova.model;

import org.json.JSONObject;

import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
@Entity
public class KeyPair extends AbstractEntity {

	/**
	 * 
	 */
	@Property("public_key")
	private String publicKey;
	@Property("fingerprint")
	private String fingerPrint;
	@Property("private_key")
	private String privateKey;
	@Property("user_id")
	private String userId;
	private static final long serialVersionUID = -5328121718576284239L;
	
	public KeyPair() {
		super();
	}

	public KeyPair(JSONObject keypair) {
		super(keypair);
	}

	public KeyPair(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public KeyPair(String s) {
		this(new JSONObject(s));
	}
	public String getPublicKey() {
		return publicKey;
	}

	public String getFingerPrint() {
		return fingerPrint;
	}
	
	public String getPrivateKey() {
		return privateKey;
	}

	public String getUserId() {
		return userId;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	@Override
	public boolean isValid() {
		return name != null;
	}
}
