package backend.model.figures;

import java.util.ArrayList;
import java.util.List;

public class Ellipse extends Figure {



    protected final Point centerPoint;
    protected final double mayorAxis, minorAxis;

    public Ellipse(Point centerPoint, double sMayorAxis, double sMinorAxis) {
        this.centerPoint = centerPoint;
        this.mayorAxis = sMayorAxis;
        this.minorAxis = sMinorAxis;
    }


    public Point getCenterPoint() {
        return centerPoint;
    }

    public double getsMayorAxis() {
        return mayorAxis;
    }

    public double getsMinorAxis() {
        return minorAxis;
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
        return String.format("Centro: %s, DMayor: %.2f, DMenor: %.2f", centerPoint, mayorAxis, minorAxis);
    }

    public String getName() {
        return "Elipse";
    }

    @Override
    public Figure copyScaled(double scaleX, double scaleY) {
        double newSMayorAxis = mayorAxis * scaleX;
        double newSMinorAxis = minorAxis * scaleY;
        double newCenterX = centerPoint.getX() + mayorAxis * (scaleX - 1);
        double newCenterY = centerPoint.getY() + minorAxis * (scaleY - 1);
        return new Ellipse(new Point(newCenterX, newCenterY), newSMayorAxis, newSMinorAxis);
    }
    @Override
    public List<Figure> divideHorizontally(int parts) {
        if (parts <= 0) throw new IllegalArgumentException("parts must be > 0");
        List<Figure> slices = new ArrayList<>(parts);
        double sliceW = mayorAxis / parts;
        double leftX = centerPoint.getX() - mayorAxis / 2;
        double cy = centerPoint.getY();

        for (int i = 0; i < parts; i++) {
            // cada sub-ellipse conserva el mismo alto (minorAxis)
            // y tiene ancho sliceW, centrada en sub-centro
            double subCenterX = leftX + sliceW * (i + 0.5);
            slices.add(new Ellipse(new Point(subCenterX, cy), sliceW, minorAxis));
        }
        return slices;
    }
    @Override
    public List<Figure> divideVertically(int parts) {
        if (parts <= 0) throw new IllegalArgumentException("parts must be > 0");
        List<Figure> slices = new ArrayList<>(parts);
        double sliceH = minorAxis / parts;
        double topY = centerPoint.getY() - minorAxis / 2;
        double cx   = centerPoint.getX();

        for (int i = 0; i < parts; i++) {
            double subCenterY = topY + sliceH * (i + 0.5);
            slices.add(new Ellipse(new Point(cx, subCenterY), mayorAxis, sliceH));
        }
        return slices;
    }

}
