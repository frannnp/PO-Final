package backend.model;

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

    public String getParameters(){
        return String.format("%s , %s", topLeft, bottomRight);
    }
}
