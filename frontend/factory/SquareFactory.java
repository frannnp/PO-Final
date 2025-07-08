package frontend.factory;

import backend.CanvasState;
import backend.model.figures.Figure;
import backend.model.figures.Point;
import backend.model.figures.Square;
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
