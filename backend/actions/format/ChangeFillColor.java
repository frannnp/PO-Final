package backend.actions.format;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;
import backend.model.format.ColorData;

public class ChangeFillColor implements Action {
    private final CanvasState canvas;
    private final ColorData oldColor;
    private final ColorData newColor;
    private final Figure figure;

    public ChangeFillColor(CanvasState canvas, Figure f,ColorData newColor) {
        this.canvas = canvas;
        this.figure = f;
        this.oldColor = figure.getFormat().getFillColor();
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        canvas.setFillColor(figure,newColor);
    }

    @Override
    public void undo() {
        canvas.setFillColor(figure,oldColor);
    }
}

