package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;

public class MoveFigure implements Action {
    private final CanvasState canvas;
    private final Figure figure;
    private final double dx,dy;
    public MoveFigure(CanvasState c, Figure figure, double dx, double dy) {
        this.canvas = c;
        this.figure = figure;
        this.dx = dx;
        this.dy = dy;
    }
    @Override
    public void execute() {
        canvas.moveFigure(figure,dx,dy);
    }

    @Override
    public void undo() {
        canvas.moveFigure(figure,-dx,-dy);
    }
}
