package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Divide una figura (Rect o Elipse) a lo alto en N partes.
 */
public class MultiplyFigure implements Action {
    private final CanvasState canvas;
    private final Figure original;
    private final int parts;
    private final List<Figure> created = new ArrayList<>();

    public MultiplyFigure(CanvasState canvas, Figure original, int parts) {
        if (parts <= 0) throw new IllegalArgumentException("N debe ser >0");
        this.canvas   = canvas;
        this.original = original;
        this.parts    = parts;
    }

    @Override
    public void execute() {
        canvas.deleteFigure(original);

        double w, h, startX, startY;
        if (original instanceof Rectangle) {
            Rectangle r = (Rectangle) original;
            w      = r.width();
            h      = r.height();
            startX = r.getTopLeft().getX();
            startY = r.getTopLeft().getY();
            double sliceH = h / parts;

            for (int i = 0; i < parts; i++) {
                Point tl = new Point(startX, startY + i * sliceH);
                Point br = new Point(startX + w, startY + (i + 1) * sliceH);
                Rectangle part = new Rectangle(tl, br);
                canvas.addFigure(part);
                created.add(part);
            }
        } else if (original instanceof Ellipse) {
            Ellipse e = (Ellipse) original;
            w      = e.getsMayorAxis();
            h      = e.getsMinorAxis();
            double cx = e.getCenterPoint().getX();
            double cy = e.getCenterPoint().getY();
            double slice = h / parts;
            for (int i = 0; i < parts; i++) {
                double subCy = cy - h/2 + slice*(i + 0.5);
                Ellipse part = new Ellipse(new Point(cx, subCy), w, slice);
                canvas.addFigure(part);
                created.add(part);
            }
        }
    }

    @Override
    public void undo() {
        created.forEach(f -> canvas.deleteFigure(f));
        created.clear();
        canvas.addFigure(original);
    }
}