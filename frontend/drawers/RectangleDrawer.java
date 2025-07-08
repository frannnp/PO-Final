package frontend.drawers;

import backend.model.figures.Figure;
import backend.model.figures.Rectangle;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;


public class RectangleDrawer extends FigureDrawer{
    public RectangleDrawer() {
    }

    @Override
    public void draw(GraphicsContext gc, FigureFormat format, Figure figure){
        Rectangle rectangle = (Rectangle) figure;
        super.predraw(gc,format,rectangle);
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
    }
}
