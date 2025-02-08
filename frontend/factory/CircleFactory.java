package frontend.factory;

import backend.CanvasState;
import backend.model.Circle;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public class CircleFactory extends EllipseFactory{
    public CircleFactory(PaintPane paintpane, CanvasState canvas) {
        super(paintpane, canvas);
    }

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint){
        return new Circle (startPoint, startPoint.distanceTo(endPoint));
    }
}
