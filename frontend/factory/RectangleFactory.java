package frontend.factory;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Rectangle;
import frontend.PaintPane;

public class RectangleFactory extends FigureFactory{

    public RectangleFactory(PaintPane paintpane, CanvasState canvas) {
        super(paintpane, canvas);
    }

    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Rectangle(startPoint, endPoint);
    }
}
