package frontend.drawers;

import backend.model.Ellipse;
import backend.model.Figure;
import backend.model.Rectangle;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

import java.time.format.FormatStyle;

public abstract class FigureDrawer {
    public void predraw(GraphicsContext gc, FigureFormat format, Figure figure){
        format.getBorderStyle().setBorder(gc);
    }
    public void postdraw(GraphicsContext gc, FigureFormat format, Figure figure){
     //   format.getBorderStyle().setBorder(gc);
    }
    public abstract void draw(GraphicsContext gc, FigureFormat format, Figure figure);

}
