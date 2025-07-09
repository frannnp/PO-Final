package frontend.factory;


import backend.model.figures.Ellipse;
import backend.model.figures.Figure;
import backend.model.figures.Point;

public class EllipseFactory extends FigureFactory{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Ellipse(startPoint, 2*Math.abs(startPoint.getX() - endPoint.getX()),2*Math.abs(startPoint.getY() - endPoint.getY()));
    }
}
