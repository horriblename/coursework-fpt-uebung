package fpt.uebung;

public class uebung6 {
	public static void main() {
		// a)
		var o1 = new Point(0, 0);
		var o2 = new Point(0, 0);

		System.err.printf("(0, 0) equals (0, 0): %b\n", o1.equals(o2));

		// b)
	}
}

class Point {
	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int n) {
		x = n;
	}

	public int getY() {
		return y;
	}

	public void setY(int n) {
		y = n;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof Point))
			return false;

		Point point = (Point) other;
		return x == point.x && y == point.y;
	}

	@Override
	public int hashCode() {
		int result = Integer.hashCode(x);
		result = 31 * result + Integer.hashCode(y);
		return result;
	}
}

class ColorPoint {
	ColorRGB color;
	Point point;

	public ColorRGB getColor() {
		return color;
	}

	public void setColor(ColorRGB n) {
		color = n;
	}
}

record ColorRGB(byte red, byte green, byte blue) {
}

// class CounterPoint extends Point {
// static int counter = 0;
//
// public CounterPoint() {
// counter++;
// }
// }
