package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.effects.EffectType;
import backend.model.figures.*;
import backend.model.format.FigureFormatData;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Divide una figura (Rect o Elipse) a lo alto en N partes.
 */
public class DivideVertical implements Action {
    private final CanvasState canvas;
    private final Figure original;
    private final int parts;
    private final List<Figure> created = new ArrayList<>();
    private final FigureFormatData origFormat;
    private final EnumSet<EffectType> origEffects;

    public DivideVertical(CanvasState canvas, Figure original, int parts) {
        if (parts <= 0) throw new IllegalArgumentException("N debe ser >0");
        this.canvas   = canvas;
        this.original = original;
        this.parts    = parts;
        this.origFormat   = original.getFormat().copy();
        this.origEffects  = EnumSet.copyOf(original.getEffects());
    }


    @Override
    public void execute() {
        canvas.deleteFigure(original);
        List<Figure> slices = original.divideVertically(parts);
        for (Figure f : slices) {
            f.setFormat(origFormat.copy());
            f.setEffects(EnumSet.copyOf(origEffects));
            canvas.addFigure(f);
            created.add(f);
        }
    }

    @Override
    public void undo() {
        created.forEach(f -> canvas.deleteFigure(f));
        created.clear();
        canvas.addFigure(original);
    }
    public List<Figure> getCreated() {
        return created;
    }
}