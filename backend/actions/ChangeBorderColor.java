package backend.actions;
import backend.model.Figure;
import frontend.FigureFormat;
import javafx.scene.paint.Color;

public class ChangeBorderColor implements Action {
    private final FigureFormat format;
    private final Color oldColor;
    private final Color newColor;

    public ChangeBorderColor(FigureFormat format, Color oldColor, Color newColor) {
        this.format = format;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void execute() {
       format.setLineColor(newColor);
    }

    @Override
    public void undo() {
        format.setLineColor(oldColor);
    }
}