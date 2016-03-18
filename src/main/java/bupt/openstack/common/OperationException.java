package bupt.openstack.common;

public class OperationException extends Exception {
	private static final long serialVersionUID = -2198325152244801838L;
	protected String message;
	protected int code;

	public OperationException() {
	}

	public OperationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public OperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperationException(String message) {
		super(message);
	}

	public OperationException(Throwable cause) {
		super(cause);
	}
	public OperationException(String format, Object ...args) {
		this(String.format(format, args));
	}
}