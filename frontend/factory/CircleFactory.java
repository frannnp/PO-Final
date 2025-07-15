package frontend.factory;

import backend.model.figures.Circle;
import backend.model.figures.Figure;
import backend.model.figures.Point;

public class CircleFactory extends EllipseFactory{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint){
        return new Circle (startPoint, startPoint.distanceTo(endPoint));
    }
}
