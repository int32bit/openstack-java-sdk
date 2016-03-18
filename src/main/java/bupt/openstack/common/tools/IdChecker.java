package bupt.openstack.common.tools;

import java.util.regex.Pattern;

public class IdChecker {
	private static final Pattern idPattern = Pattern.compile("\\w{8}-(\\w{4}-){3}\\w{12}");
	public static boolean isId(String s) {
		if ((s == null) || (s.length() < 1))
			return false;
		return idPattern.matcher(s).matches();
	}
}
