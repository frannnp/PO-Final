package frontend.factory;

import backend.CanvasState;
import backend.model.Figure;
import backend.model.Point;
import frontend.PaintPane;

public abstract class FigureFactory {
    private final PaintPane paintPane;
    private final CanvasState canvas;
    public FigureFactory(PaintPane paintpane, CanvasState canvas){
        this.paintPane = paintpane;
        this.canvas = canvas;
    }
    public void createFigure(Point startPoint, Point endPoint){
        Figure figure = generateFigure(startPoint,endPoint);
        //figure.setFormat(paintpane.getFormat());
        canvas.addFigure(figure);
    }
    public abstract Figure generateFigure(Point startPoint, Point endPoint);
}
