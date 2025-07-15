package backend.actions.effect;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.effects.EffectType;
import backend.model.figures.Figure;

public class AddEffect implements Action {
    private final CanvasState canvas;
    private final Figure selectedFigure;
    private final EffectType effect;

    @Override
    public void execute() {
            canvas.addEffect(selectedFigure, effect);
    }
    public AddEffect(CanvasState canvas, Figure selectedFigure, EffectType effect) {
        this.canvas = canvas;
        this.selectedFigure = selectedFigure;
        this.effect = effect;
    }
    @Override
    public void undo() {
        canvas.removeEffect(selectedFigure, effect);
    }
}
