package backend.actions;

import backend.CanvasState;
import backend.model.Figure;

public class DeleteFigure extends AddFigure{

    public DeleteFigure(CanvasState canvas, Figure figure) {
        super(canvas, figure);
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
