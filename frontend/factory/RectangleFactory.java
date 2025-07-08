package frontend.factory;

import backend.CanvasState;
import backend.model.figures.Figure;
import backend.model.figures.Point;
import backend.model.figures.Rectangle;
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
