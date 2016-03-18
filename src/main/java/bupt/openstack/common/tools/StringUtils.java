package bupt.openstack.common.tools;

import java.util.Map;

public class StringUtils {
	private StringUtils() {
		
	}
	/**
	 * 检测字符串是否为空
	 * @param s 检测的字符串对象
	 * @return 如果s为null或者长度为0，返回true， 否则返回false
	 */
	public static boolean isBlank(String s) {
		return s == null || s.length() <= 0;
	}
	/**
	 * 检测该字符串不为空
	 * @param s 检测字符串对象
	 * @return 如果字符串不为null且长度大于0，返回true，否则返回false
	 */
	public static boolean isNotBlank(String s) {
		return !isBlank(s);
	}
	/**
	 * 为空字符串提供默认值。
	 * @param s 目标字符串对象
	 * @param other 默认字符串对象
	 * @return 如果目标字符串对象不为空，返回目标字符串，否则返回默认字符串对象
	 */
	public static String orElse(String s, String other) {
		if (isBlank(s)) {
			return other;
		}
		return s;
	}
	/**
	 * 使用指定参数格式化字符串。<br/>
	 * 比如：s = "Hi, {name}, today is {date}"；<br/>
	 * StringUtils.format(s, "Mary", new Date());<br/>
	 * 返回： Hi, Mary, today is Wed Oct 29 10:52:52 CST 2014
	 * @param s 原始未被格式化的字符串
	 * @param args 参数列表，参数个数不能少于原始字符串参数个数。
	 * @return
	 */
	public static String format (String s, Object ...args) {
		ArgumentFormatter api = new ArgumentFormatter(s);
		return api.format(args).get();
	}
	/**
	 * 使用指定参数格式化字符串 <br/>
	 * 比如：s = "Hi, {name}, today is {date}";<br/>
	 * Map<String, Object> mapper = new HashMap<>();<br/>
	 * mapper.put("name", "Mary");<br/>
	 * mapper.put("date", new Date());<br/>
	 * StringUtils.format(s, mapper);<br/>
	 * 返回：Hi, Mary, today is Wed Oct 29 10:52:52 CST 2014
	 * @param s 原始未被格式化的字符串
	 * @param mapper 参数映射，必须包含所有原始字符串代参数。
	 * @return
	 */
	public static String format(String s, Map<String, ?> mapper) {
		ArgumentFormatter api= new ArgumentFormatter(s);
		return api.format(mapper).get();
	}
	public static String getOrElse(String s, String defaultValue) {
		if (isNotBlank(s)) {
			return s;
		}
		return defaultValue;
	}
}
