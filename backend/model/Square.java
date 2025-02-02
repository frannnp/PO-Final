package backend.model;

public class Square implements Rectangle {

    public Square(Point topLeft, double size) {
        super(topLeft, new Point(topLeft.x + size, topLeft.y + size));
    }
    
    @Override
    public String toString() {
        return String.format("Cuadrado [ %s , %s ]", topLeft, bottomRight);
    }

}
