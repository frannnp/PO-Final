package backend.actions;

import backend.CanvasState;
import backend.model.figures.Figure;

public class BringToFront implements Action {
    private final CanvasState canvas;
    private final Figure figure;

    public BringToFront(CanvasState canvasState, Figure figure) {
        this.canvas = canvasState;
        this.figure = figure;
    }

    @Override
    public void execute() {
        canvas.sendToTop(figure);
    }

    @Override
    public void undo() {
        canvas.sendToBottom(figure);
    }
}