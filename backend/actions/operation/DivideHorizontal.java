package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;

import java.util.ArrayList;
import java.util.List;

public class DivideHorizontal implements Action {
    private final CanvasState canvas;
    private final Figure original;
    private final int parts;
    private final List<Figure> created = new ArrayList<>();

    public DivideHorizontal(CanvasState canvas, Figure original, int parts) {
        if (parts <= 0) throw new IllegalArgumentException("N debe ser >0");
        this.canvas   = canvas;
        this.original = original;
        this.parts    = parts;
    }

    @Override
    public void execute() {
        canvas.deleteFigure(original);



    }

    @Override
    public void undo() {
        // borro creadas y re-agrego original
        for (Figure f : created) {
            canvas.deleteFigure(f);
        }
        created.clear();
        canvas.addFigure(original);
    }
}
