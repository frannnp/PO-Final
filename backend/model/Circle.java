package backend.model;

public class Circle extends Ellipse {

    protected final double radius;
    //checkear esto
    public Circle(Point centerPoint, double radius) {
        super(centerPoint, 2 * radius, 2 * radius);
        this.radius = radius;
    }

    /*@Override
    public String toString() {
        return String.format("Círculo [Centro: %s, Radio: %.2f]", centerPoint, radius);
    }
    ¨*/
    @Override
    public String getFormat() {
        return String.format("Centro: %s, Radio: %.2f", centerPoint, radius);
    }

    @Override
    public String getName() {
        return "Circulo";
    }

    public double getRadius() {
        return radius;
    }

}
