package frontend.drawers;

import backend.model.figures.Figure;
import backend.model.figures.Rectangle;
import javafx.scene.canvas.GraphicsContext;


public class RectangleDrawer extends FigureDrawer{


    @Override
    protected void drawBorder(GraphicsContext gc, Figure figure) {
        Rectangle rectangle = (Rectangle) figure;
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
    }

    @Override
    protected void drawFill(GraphicsContext gc, Figure figure) {
        Rectangle rectangle = (Rectangle) figure;
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
    }

    @Override
    protected void drawHorizontalMirror(GraphicsContext gc, Figure figure) {
        Rectangle rectangle = (Rectangle) figure;
        gc.fillRect(rectangle.getTopLeft().getX()+rectangle.width(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
    }

    @Override
    protected void drawVerticalMirror(GraphicsContext gc, Figure figure) {
        Rectangle rectangle = (Rectangle) figure;
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY()- rectangle.height(), rectangle.width(),rectangle.height());
    }
}
