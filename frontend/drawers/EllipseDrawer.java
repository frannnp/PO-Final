package frontend.drawers;

import backend.model.Ellipse;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

public class EllipseDrawer extends FigureDrawer{

    private Ellipse ellipse;
    public EllipseDrawer(Ellipse ellipse) {
        this.ellipse = ellipse;
    }
    @Override
    public void draw(GraphicsContext gc, FigureFormat format){
        super.draw(gc,format);
        gc.fillOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
        gc.strokeOval(ellipse.getCenterPoint().getX() - ellipse.getsMayorAxis()/2, ellipse.getCenterPoint().getY() - ellipse.getsMinorAxis()/2, ellipse.getsMayorAxis(), ellipse.getsMinorAxis());
    }

}
