package frontend.drawers;

import backend.model.figures.Ellipse;
import backend.model.figures.Figure;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

public class EllipseDrawer extends FigureDrawer{


    @Override
    protected void drawFill(GraphicsContext gc, Figure f) {
        Ellipse ellipse = (Ellipse) f;
        gc.fillOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

    @Override
    protected void drawBorder(GraphicsContext gc, Figure f) {
        Ellipse ellipse = (Ellipse) f;
        gc.strokeOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
    @Override
    protected void drawHorizontalMirror(GraphicsContext gc, Figure f) {
        Ellipse ellipse = (Ellipse) f;
        gc.fillOval(-ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }
    @Override
    protected void drawVerticalMirror(GraphicsContext gc, Figure f) {
        Ellipse ellipse = (Ellipse) f;
        gc.fillOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, -ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

}
