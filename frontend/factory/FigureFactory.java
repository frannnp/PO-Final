package frontend.factory;


import backend.model.figures.Figure;
import backend.model.figures.Point;


public abstract class FigureFactory {

    public abstract Figure generateFigure(Point startPoint, Point endPoint);
}
