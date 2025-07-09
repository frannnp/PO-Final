package frontend.factory;

import backend.CanvasState;
import backend.model.figures.Circle;
import backend.model.figures.Figure;
import backend.model.figures.Point;
import frontend.PaintPane;

public class CircleFactory extends EllipseFactory{

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint){
        return new Circle (startPoint, startPoint.distanceTo(endPoint));
    }
}
