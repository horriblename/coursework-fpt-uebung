package fpt.uebung;

class uebung6 {
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

	public boolean equals(Point other) {
		return x == other.x && y == other.y;
	}
}

class ColorPoint {
	Color color;
	Point point;

	public Color getColor() {
		return color;
	}

	public void setColor(Color n) {
		color = n;
	}
}

// record Color(byte red, byte green, byte blue) {
// }

// class CounterPoint extends Point {
// static int counter = 0;
//
// public CounterPoint() {
// counter++;
// }
// }
