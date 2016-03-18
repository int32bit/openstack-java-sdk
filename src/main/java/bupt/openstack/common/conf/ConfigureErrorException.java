package bupt.openstack.common.conf;

public class ConfigureErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -408306260315944562L;

	public ConfigureErrorException() {
		super();
	}

	public ConfigureErrorException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfigureErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigureErrorException(String message) {
		super(message);
	}

	public ConfigureErrorException(Throwable cause) {
		super(cause);
	}
	public ConfigureErrorException(String format, Object ...args) {
		super(String.format(format, args));
	}
}
