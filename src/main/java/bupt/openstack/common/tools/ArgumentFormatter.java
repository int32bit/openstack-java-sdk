package bupt.openstack.common.tools;

import java.util.Map;
import java.util.MissingFormatArgumentException;
/**
 * 
 * @author fgp
 *
 */
public class ArgumentFormatter {
	private String origin;
	private StringBuilder formatted;
	private boolean hasFormatted = false;
	public ArgumentFormatter(String value) {
		this.origin = value;
		this.formatted = new StringBuilder(origin);
	}
	public ArgumentFormatter(String value, Object ...args) {
		this(value);
		format(args);
	}
	public ArgumentFormatter(String value, Map<String, ? extends Object> mapper) {
		this(value);
		format(mapper);
	}
	public ArgumentFormatter format(Object ...args) {
		if (hasFormatted()) {
			return this;
		}
		StringBuilder sb = new StringBuilder(formatted);
		for (int i = 0; i < args.length; ++i) {
			int start = sb.indexOf("{");
			int end = sb.indexOf("}");
			if (start < 0 || end < 0) {  /* 没有选项值，结束 */
				this.formatted = sb; /* update formatted */
				this.hasFormatted = true;
				return this;
			}
			sb.replace(start, end + 1, args[i].toString());
		}
		int start = sb.indexOf("{");
		int end = sb.indexOf("}");
		
		if (start >= 0 && end >= 0) {
			String sub = sb.substring(start + 1, end);
			throw new MissingFormatArgumentException("Format specifier '" + sub + "'");
		}
		/* update formatted */
		this.formatted = sb;
		this.hasFormatted = true;
		return this;
	}
	public ArgumentFormatter format(Map<String, ? extends Object> mapper) {
		if (hasFormatted())
			return this;
		StringBuilder sb = new StringBuilder(formatted);
		while(true) {
			int start = sb.indexOf("{");
			int end = sb.indexOf("}");
			if (start < 0 || end < 0) {
				break;
				
			}
			String key = sb.substring(start + 1, end);
			Object value = mapper.get(key);
			if (value == null) {
				throw new MissingFormatArgumentException("Format specifier '" + key + "'");
			}
			sb.replace(start, end + 1, value.toString());
		}
		/* update */
		this.formatted = sb;
		this.hasFormatted = true;
		return this;
	}
	public ArgumentFormatter set(String key, Object value) {
		StringBuilder sb = formatted;
		int pos = sb.indexOf('{' + key + '}');
		if (pos < 0) {
			boolean hasKey = hasKey(key);
			if (hasKey) {
				throw new FormattedException("Should not resign the key'" + key + "'!");
			} else {
				throw new FormattedException("The key '" + key + "' Not Found!");
			}
		}
		int start = pos;
		int end = start + key.length() + 1;
		sb.replace(start, end + 1, value.toString());
		return this;
	}
	/**
	 * test if it has been formatted.
	 * @return true if it has been formatted, false else.
	 */
	public boolean hasFormatted() {
		if (hasFormatted) {
			return true;
		}
		boolean hasRemains = formatted.toString().matches(".*\\{.+\\}.*");
		if (!hasRemains) {
			this.hasFormatted = true;
			return true;
		}
		return false;
	}
	public boolean hasKey(String key) {
		String k = "{" + key + "}";
		return origin.indexOf(k) >= 0;
	}
	@Override
	public String toString() {
		return formatted.toString();
	}
	/**
	 * Get formatted String, if it has not been formatted, throws RuntimeException.
	 * 	
	 * @return formatted String.
	 */
	public String get() {
		if (!hasFormatted()) {
			throw new APIHasNotFormattedException("api has not formatted");
		}
		return formatted.toString();
	}
	/**
	 * Reset the Object, you can resign the value later.
	 * @return this;
	 */
	public ArgumentFormatter reset() {
		this.formatted = new StringBuilder(origin);
		this.hasFormatted = false;
		return this;
	}
	/**
	 * Reset the Object with new value.
	 * @param value the new value.
	 * @return itself
	 */
	public ArgumentFormatter reset(String value) {
		this.origin = value;
		this.formatted = new StringBuilder(origin);
		this.hasFormatted = false;
		return this;
	}
	/**
	 * get the unformatted String.
	 * @return origin String without format
	 */
	public String getOrigin() {
		return origin;
	}
	public static class APIHasNotFormattedException extends RuntimeException {
		private static final long serialVersionUID = 3122564978951809137L;
		public APIHasNotFormattedException() {
			super();
		}

		public APIHasNotFormattedException(String message, Throwable cause,
				boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public APIHasNotFormattedException(String message, Throwable cause) {
			super(message, cause);
		}

		public APIHasNotFormattedException(String message) {
			super(message);
		}
		public APIHasNotFormattedException(Throwable cause) {
			super(cause);
		}
	}
	public static class FormattedException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8555904731926715590L;

		public FormattedException() {
			super();
		}

		public FormattedException(String message, Throwable cause,
				boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public FormattedException(String message, Throwable cause) {
			super(message, cause);
		}

		public FormattedException(String message) {
			super(message);
		}

		public FormattedException(Throwable cause) {
			super(cause);
		}
	}
}
