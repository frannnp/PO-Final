package backend.model;

public class Rectangle extends AbstractFigure {

    private final Point topLeft, bottomRight;
    private static final String name = "Rectángulo";

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


    public String getName(){
        return name;
    }
    @Override
    public String toString() {
        return String.format("%s [ %s , %s ]", getName(), topLeft, bottomRight);
    }

}
