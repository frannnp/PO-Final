package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.effects.EffectType;
import backend.model.figures.*;
import backend.model.format.FigureFormatData;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MultiplyFigure implements Action {
    private final CanvasState canvas;
    private final Figure original;
    private final int times;
    private final List<Figure> created = new ArrayList<>();
    private final FigureFormatData origFormat;
    private final EnumSet<EffectType> origEffects;

    private static final double OFFSET_X = 20;
    private static final double OFFSET_Y = 20;

    public MultiplyFigure(CanvasState canvas, Figure original, int times) {
        if (times <= 0) throw new IllegalArgumentException("times debe ser > 0");
        this.canvas      = canvas;
        this.original    = original;
        this.times       = times;
        this.origFormat  = original.getFormat().copy();
        this.origEffects = EnumSet.copyOf(original.getEffects());
    }

    @Override
    public void execute() {
        for (int i = 1; i <= times; i++) {
            Figure copy = original.clone();
            copy.move(i * OFFSET_X, i * OFFSET_Y);
            copy.setFormat(origFormat.copy());
            copy.setEffects(EnumSet.copyOf(origEffects));
            canvas.addFigure(copy);
            created.add(copy);
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