package frontend.factory;

import backend.model.figures.Figure;
import backend.model.figures.Point;
import backend.model.figures.Rectangle;


public class RectangleFactory extends FigureFactory{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }
}
