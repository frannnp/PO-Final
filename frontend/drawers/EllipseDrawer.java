package frontend.drawers;

import backend.model.figures.Ellipse;
import backend.model.figures.Figure;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

public class EllipseDrawer extends FigureDrawer{

    @Override
    public void draw(GraphicsContext gc, FigureFormat format, Figure figure){
        Ellipse ellipse = (Ellipse) figure;
        super.predraw(gc,format,ellipse);
        gc.fillOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.strokeOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

}
