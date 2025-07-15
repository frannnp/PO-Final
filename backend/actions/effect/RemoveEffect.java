package backend.actions.effect;

import backend.CanvasState;
import backend.model.effects.EffectType;
import backend.model.figures.Figure;

public class RemoveEffect extends AddEffect {
    public RemoveEffect(CanvasState canvas,  Figure selectedFigure, EffectType effectType) {
        super(canvas, selectedFigure,effectType);
    } //todo

    @Override
    public void execute() {
        super.undo();
    }

    @Override
    public void undo() {
        super.execute();
    }
}
