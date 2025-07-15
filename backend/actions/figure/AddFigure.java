package backend.actions.figure;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;

import java.util.EnumSet;
import backend.model.effects.EffectType;
import backend.model.format.FigureFormatData;

public class AddFigure implements Action {
    private final FigureFormatData format;
    private final EnumSet<EffectType> effects;
    private final CanvasState canvas;
    private final Figure figure;

    public AddFigure(CanvasState canvas, Figure figure, FigureFormatData format, EnumSet<EffectType> effects) {
        this.format = format;
        this.effects = effects;
        this.canvas = canvas;
        this.figure = figure;
    }

    @Override
    public void execute() {
        canvas.addFigure(this.figure);
        figure.setFormat(format);
        figure.setEffects(effects);
    }

    @Override
    public void undo() {
        canvas.deleteFigure(figure);
    }
}
