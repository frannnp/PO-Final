package frontend.factory;

import backend.model.figures.Figure;
import backend.model.figures.Point;
import backend.model.figures.Square;

public class SquareFactory extends RectangleFactory{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Square(startPoint,Math.abs(endPoint.getX() - startPoint.getX()) );
    }

}
