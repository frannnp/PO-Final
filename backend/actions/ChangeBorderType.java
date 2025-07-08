package backend.actions;
import frontend.BorderStyle;
import frontend.FigureFormat;

public class ChangeBorderType implements Action {
    private final FigureFormat format;
    private final BorderStyle oldBorderType;
    private final BorderStyle newBorderType;

    public ChangeBorderType(FigureFormat format, BorderStyle oldBorderType, BorderStyle newBorderType) {
        this.format = format;
        this.oldBorderType = oldBorderType;
        this.newBorderType = newBorderType;
    }

    @Override
    public void execute() {
        format.setBorderStyle(newBorderType);
    }

    @Override
    public void undo() {
        format.setBorderStyle(oldBorderType);
    }
}