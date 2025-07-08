package backend.actions.format;
import backend.actions.Action;
import frontend.FigureFormat;
import javafx.scene.paint.Color; //todo sacar esto

public class ChangeBorderColor implements Action {
    private final FigureFormat format;
    private final ColorData oldColor;
    private final ColorData newColor;

    public ChangeBorderColor(FigureFormat format, ColorData oldColor, ColorData newColor) {
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