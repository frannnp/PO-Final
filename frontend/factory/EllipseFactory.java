package frontend.factory;

import backend.CanvasState;
import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public class EllipseFactory extends FigureFactory{
    public EllipseFactory(PaintPane paintpane, CanvasState canvas) {
        super(paintpane, canvas);
    }

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Ellipse(startPoint, 2*Math.abs(startPoint.getX() - endPoint.getX()),2*Math.abs(startPoint.getY() - endPoint.getY()));
    }
}
