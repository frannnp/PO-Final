package backend.model.figures;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Figure {

    private final Point topLeft, bottomRight;

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }
    public double width(){ return topLeft.horizontalDistanceTo(bottomRight);}
    public double height(){ return topLeft.verticalDistanceTo(bottomRight);}


    @Override
    public void move(double dx, double dy) {
        topLeft.move(dx, dy);
        bottomRight.move(dx, dy);
    }
    @Override
    public boolean belongs(Point p) {
        return p.getX() > topLeft.getX() && p.getX() < bottomRight.getX() &&
                p.getY() > topLeft.getY() && p.getY() < bottomRight.getY();
    }

    public String getName(){
        return "Rectángulo";
    }

    @Override
    public Figure copyScaled(double scaleX, double scaleY) {
        double width = width();
        double height = height();
        return new Rectangle(
                new Point(topLeft.getX(), topLeft.getY()),
                new Point(topLeft.getX() + width * scaleX, topLeft.getY() + height * scaleY)
        );
    }
    @Override
    public List<Figure> divideHorizontally(int parts) {
        if (parts <= 0) throw new IllegalArgumentException("parts must be > 0");
        List<Figure> slices = new ArrayList<>(parts);
        double sliceW = width() / (double) parts;
        for (int i = 0; i < parts; i++) {
            Figure slice = this.copyScaled(1.0 / parts, 1.0 /parts);
            slice.move(i * sliceW, height()/2-sliceW);
            slices.add(slice);
        }
        return slices;
    }
    @Override
    public List<Figure> divideVertically(int parts) {
        if (parts <= 0) throw new IllegalArgumentException("parts must be > 0");
        List<Figure> slices = new ArrayList<>(parts);
        double sliceH = height() / (double)parts;

        for (int i = 0; i < parts; i++) {
            Figure slice = this.copyScaled(1.0/parts, 1.0 / parts);
            slice.move(width()/2-sliceH, i * sliceH);
            slices.add(slice);
        }
        return slices;
    }

    public String getParameters(){
        return String.format("%s , %s", topLeft, bottomRight);
    }
}
