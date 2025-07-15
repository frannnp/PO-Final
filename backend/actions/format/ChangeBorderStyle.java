package backend.actions.format;
import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;
import backend.model.format.borderStyle;

public class ChangeBorderStyle implements Action {
    private final CanvasState canvas;
    private final Figure figure;
    private final borderStyle oldBorder;
    private final borderStyle newBorder;

    public ChangeBorderStyle(CanvasState canvas, Figure f, borderStyle newBorder) {
        this.canvas = canvas;
        this.figure = f;
        this.oldBorder = f.getFormat().getBorderStyle();
        this.newBorder = newBorder;
    }

    @Override
    public void execute() {
        canvas.setBorder(figure,newBorder);
    }

    @Override
    public void undo() {
        canvas.setBorder(figure,oldBorder);
    }
}