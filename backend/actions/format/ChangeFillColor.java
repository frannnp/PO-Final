package backend.actions.format;

import backend.actions.Action;
import backend.format.ColorData;
import backend.format.FigureFormatData;

public class ChangeFillColor implements Action {
    private final FigureFormatData format;
    private final ColorData oldColor;
    private final ColorData newColor;
    public ChangeFillColor(FigureFormatData format, ColorData oldColor, ColorData newColor) {
        this.format = format;
        this.oldColor = oldColor;
        this.newColor = newColor;
    }

    @Override
    public void execute() {
        format.setFillColor(newColor);
    }

    @Override
    public void undo() {
        format.setFillColor(oldColor);
    }
}

