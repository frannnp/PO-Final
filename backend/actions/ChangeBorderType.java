package backend.actions;
import backend.model.Figure;

public class ChangeBorderType implements Action {
    private final Figure figure;
    private final String oldBorderType;
    private final String newBorderType;

    public ChangeBorderType(Figure figure, String oldBorderType, String newBorderType) {
        this.figure = figure;
        this.oldBorderType = oldBorderType;
        this.newBorderType = newBorderType;
    }

    @Override
    public void execute() {
    //    figure.setBorderType(newBorderType);
    }

    @Override
    public void undo() {
    //    figure.setBorderType(oldBorderType);
    }
}