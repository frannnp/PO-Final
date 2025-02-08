package backend.actions;
import backend.CanvasState;
import backend.model.Figure;

public class SendToBack implements Action {
    private final CanvasState canvas;
    private final Figure figure;

    public SendToBack(CanvasState canvasState, Figure figure) {
        this.canvas = canvasState;
        this.figure = figure;
    }

    @Override
    public void execute() {
        canvas.sendToBottom(figure);
    }

    @Override
    public void undo() {
        canvas.sendToTop(figure);
    }
}