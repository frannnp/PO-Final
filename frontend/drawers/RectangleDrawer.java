package frontend.drawers;

import backend.model.Rectangle;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

import java.time.format.FormatStyle;

public class RectangleDrawer extends FigureDrawer{
    private final Rectangle rectangle;
    public RectangleDrawer(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void draw(GraphicsContext gc, FigureFormat format){
        super.draw(gc,format);
        gc.fillRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
        gc.strokeRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.width(),rectangle.height());
    }
}
