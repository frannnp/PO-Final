package backend.actions.format;
import backend.actions.Action;
import backend.model.figures.Figure;
import backend.model.format.borderStyle;

public class ChangeBorderStyle implements Action {
    private final Figure figure;
    private final borderStyle oldBorder;
    private final borderStyle newBorder;

    public ChangeBorderStyle(Figure f, borderStyle newBorder) {
        this.figure = f;
        this.oldBorder = f.getFormat().getBorderStyle();
        this.newBorder = newBorder;
    }

    @Override
    public void execute() {
        figure.setBorder(newBorder);
    }

    @Override
    public void undo() {
        figure.setBorder(oldBorder);
    }
}