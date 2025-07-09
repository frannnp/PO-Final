package backend.actions.operation;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;

/** Mueve una figura a unas coordenadas absolutas (x,y). */
public class TranslateFigure implements Action {
    private final Figure figure;
    private final double oldX, oldY;
    private final double newX, newY;

    public TranslateFigure(CanvasState canvas, Figure figure, double newX, double newY) {
        this.figure = figure;
        // asumimos que Figure tiene getPosition() o similar
        this.oldX   = figure.getPosition().getX();
        this.oldY   = figure.getPosition().getY();
        this.newX   = newX;
        this.newY   = newY;
    }

    @Override
    public void execute() {
        figure.moveTo(newX, newY);
    }

    @Override
    public void undo() {
        figure.moveTo(oldX, oldY);
    }
}
