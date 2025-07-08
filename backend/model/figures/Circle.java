package backend.model.figures;

public class Circle extends Ellipse {

    private final double radius;
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
    public boolean belongs(Point p){
        return Math.sqrt(Math.pow(getCenterPoint().getX() - p.getX(), 2) +
                Math.pow(getCenterPoint().getY() - p.getY(), 2)) < getRadius();
    }


    @Override
    public String getParameters() {
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
