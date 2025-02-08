package backend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class AddFigure implements Action{
    private final CanvasState canvas;
    private final Figure figure;

    public AddFigure(CanvasState canvas, Figure figure) {
        this.canvas = canvas;
        this.figure = figure;
    }

    @Override
    public void execute() {
        canvas.addFigure(this.figure);
    }

    @Override
    public void undo() {
        canvas.deleteFigure(figure);
    }
}
