package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;

/** Mueve una figura a unas coordenadas absolutas (x,y). */ //todo
public class TranslateFigure implements Action {
    private final CanvasState canvas;
    private final Figure figure;
    private final double oldX, oldY;
    private final double newX, newY;

    public TranslateFigure(CanvasState canvas, CanvasState canvas1, Figure figure, double oldX, double oldY, double newX, double newY) {
        this.canvas = canvas1;
        this.figure = figure;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX   = newX;
        this.newY   = newY;
    }

    @Override
    public void execute() {
        canvas.moveFigure(figure, oldX, oldY);
    }

    @Override
    public void undo() {
        canvas.moveFigure(figure, oldX, oldY);}
}
