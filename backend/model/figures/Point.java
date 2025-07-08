package backend.model.figures;

import backend.Movable;

public class Point implements Movable {

    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(double dx, double dy) {
        x += dx;
        y += dy;
    }

    @Override
    public String toString() {
        return String.format("{%.2f , %.2f}", x, y);
    }

    public double distanceTo(Point p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }
    public double horizontalDistanceTo(Point p) {
        return Math.abs(x - p.x);
    }
    public double verticalDistanceTo(Point p) {
        return Math.abs(y - p.y);
    }

}
