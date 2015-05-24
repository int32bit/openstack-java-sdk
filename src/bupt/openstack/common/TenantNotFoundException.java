package bupt.openstack.common;

public class TenantNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3072726153244920270L;

	public TenantNotFoundException() {
	}

	public TenantNotFoundException(String message) {
		super(message);
	}

	public TenantNotFoundException(Throwable cause) {
		super(cause);
	}

	public TenantNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TenantNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
