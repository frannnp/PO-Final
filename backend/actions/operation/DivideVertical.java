package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Divide una figura (Rect o Elipse) a lo alto en N partes.
 */
public class DivideVertical implements Action {
    private final CanvasState canvas;
    private final Figure original;
    private final int parts;
    private final List<Figure> created = new ArrayList<>();

    public DivideVertical(CanvasState canvas, Figure original, int parts) {
        if (parts <= 0) throw new IllegalArgumentException("N debe ser >0");
        this.canvas   = canvas;
        this.original = original;
        this.parts    = parts;
    }

    @Override
    public void execute() {
        canvas.deleteFigure(original);

        double w, h, startX, startY;

    }

    @Override
    public void undo() {
        created.forEach(f -> canvas.deleteFigure(f));
        created.clear();
        canvas.addFigure(original);
    }
}