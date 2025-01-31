package backend.model;

public class Rectangle extends AbstractFigure {

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

    public String getName(){
        return "Rectángulo";
    }

    public String getFormat(){
        return String.format("%s , %s", topLeft, bottomRight);
    }
}
