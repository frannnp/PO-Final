package backend.actions;
import backend.model.Figure;
import javafx.scene.paint.Color;

public class ChangeBorderColor implements Action {
    private final Figure figure;
    private final Color oldColor;
    private final Color newColor;

    public ChangeBorderColor(Figure figure, Color oldColor, Color newColor) {
        this.figure = figure;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void execute() {
    //    figure.setBorderColor(newColor);
    }

    @Override
    public void undo() {
    //    figure.setBorderColor(oldColor);
    }
}