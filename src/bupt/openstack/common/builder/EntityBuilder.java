package bupt.openstack.common.builder;

public abstract class EntityBuilder implements EntityBuilderInterface {
	protected int getFitValue(int value, int max, int min) {
		if (value - min < 0) {
			return min;
		}
		if (value - max > 0)
			return max;
		return value;
	}
	protected long getFitValue(long value, long max, long min) {
		if (value - min < 0) {
			return min;
		}
		if (value - max > 0)
			return max;
		return value;
	}
	protected double getFitValue(double value, double max, double min) {
		if (value - min < 0) {
			return min;
		}
		if (value - max > 0)
			return max;
		return value;
	}
	private int countOfOne(int n) {
		int count = 0;
		while (n != 0) {
			n &= (n - 1);
			++count;
		}
		return count;
	}
	private int countOfOne(long n) {
		int count = 0;
		while (n != 0) {
			n &= (n - 1);
			++count;
		}
		return count;
	}
	private int highOne(int n) {
		int index = 0;
		while (n != 0) {
			++index;
			n >>>= 1;
		}
		return index;
	}
	private int highOne(long n) {
		int index = 0;
		while (n != 0) {
			++index;
			n >>>= 1;
		}
		return index;
	}
	protected boolean isPow2(int n) {
		return countOfOne(n) == 1;
	}
	protected boolean isPow2(long n) {
		return countOfOne(n) == 1;
	}
	protected int lower2(int n) {
		if (isPow2(n)) {
			return n;
		}
		int offset = 1 << (highOne(n) - 1);
		return n & offset;
	}
	protected long lower2(long n) {
		if (isPow2(n)) {
			return n;
		}
		int offset = 1 << (highOne(n) - 1);
		return n & offset;
	}
	protected int higher2(int n) {
		return lower2(n) << 1;
	}
	protected long higher2(long n) {
		return lower2(n) << 1;
	}
	public int getFitPow2(int n) {
		if (n <= 0)
			return n;
		if (isPow2(n)) {
			return n;
		}
		int lower = lower2(n);
		int higher = higher2(n);
		int a = n - lower;
		int b = higher - n;
		int ans = a > b ? higher : lower;
		return ans;
	}
}
