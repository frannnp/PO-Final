package frontend.factory;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import backend.model.Square;
import frontend.PaintPane;

public class SquareFactory extends RectangleFactory{

    public SquareFactory(PaintPane paintpane, CanvasState canvas) {
        super(paintpane, canvas);
    }
    @Override
    public Figure generateFigure(Point startPoint, Point endPoint) {
        return new Square(startPoint,Math.abs(endPoint.getX() - startPoint.getX()) );
    }

}
