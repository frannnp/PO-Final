package frontend.drawers;

import backend.model.figures.Figure;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

public abstract class FigureDrawer {
    public void predraw(GraphicsContext gc, FigureFormat format, Figure figure){
        format.getBorderStyle().setBorder(gc);
    }
    public void postdraw(GraphicsContext gc, FigureFormat format, Figure figure){
     //   format.getBorderStyle().setBorder(gc);
    }
    public abstract void draw(GraphicsContext gc, FigureFormat format, Figure figure);

}
