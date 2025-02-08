package frontend.drawers;

import backend.model.Figure;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

import java.time.format.FormatStyle;

public abstract class FigureDrawer {
    public void draw(GraphicsContext gc, FigureFormat format){
        format.getBorderStyle().setBorder(gc);
    }

}
