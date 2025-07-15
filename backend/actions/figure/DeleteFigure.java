package backend.actions.figure;

import backend.CanvasState;
import backend.actions.figure.AddFigure;
import backend.model.effects.EffectType;
import backend.model.figures.Figure;
import backend.model.format.FigureFormatData;

import java.util.EnumSet;

public class DeleteFigure extends AddFigure {

    public DeleteFigure(CanvasState canvas, Figure figure) {
        super(canvas, figure, figure.getFormat(), figure.getEffects());
    }
    @Override
    public void execute() {
            super.undo();
    }

    @Override
    public void undo() {
            super.execute();
    }
}
