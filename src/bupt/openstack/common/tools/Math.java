package bupt.openstack.common.tools;

public class Math {
	public static boolean in(int value, int min, int max) {
		return value >= min && value < max;
	}
	public static boolean notIn(int value, int min, int max) {
		return ! in(value, min, max);
	}
}
