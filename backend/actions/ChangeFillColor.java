package backend.actions;

import backend.model.Figure;
import javafx.scene.paint.Color;

public class ChangeFillColor implements Action{
    private final Figure figure;
    private final Color oldColor;
    private final Color newColor;
    public ChangeFillColor(Figure figure, Color oldColor, Color newColor) {
        this.figure = figure;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void execute() {
    //    figure.setFillColor(newColor);
    }

    @Override
    public void undo() {
    //    figure.setFillColor(oldColor);
    }
}

