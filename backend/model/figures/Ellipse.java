package backend.model.figures;

public class Ellipse extends Figure {

    protected final Point centerPoint;
    protected final double sMayorAxis, sMinorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.sMayorAxis = sMayorAxis;
        this.sMinorAxis = sMinorAxis;
    }


    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return sMayorAxis;
    }

    public double getsMinorAxis() {
        return sMinorAxis;
    }
    @Override
    public void move(double deltaX, double deltaY) {
        centerPoint.move(deltaX, deltaY);
    }

    @Override
    public boolean belongs(Point p) {
        return  ((Math.pow(p.getX() - getCenterPoint().getX(), 2) / Math.pow(getsMayorAxis(), 2)) +
                (Math.pow(p.getY() - getCenterPoint().getY(), 2) / Math.pow(getsMinorAxis(), 2))) <= 0.30;
    }

    public String getParameters() {
        return String.format("Centro: %s, DMayor: %.2f, DMenor: %.2f", centerPoint, sMayorAxis, sMinorAxis);
    }

    public String getName() {
        return "Elipse";
    }

}
