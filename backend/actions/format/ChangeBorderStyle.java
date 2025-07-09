package backend.actions.format;
import backend.actions.Action;
import backend.format.FigureFormatData;
import backend.format.borderStyle;
import frontend.BorderStyle; //todo sacar

public class ChangeBorderStyle implements Action {
    private final FigureFormatData format;
    private final borderStyle oldBorderType;
    private final borderStyle newBorderType;

    public ChangeBorderStyle(FigureFormatData format, borderStyle oldBorderType, BorderStyle newBorderType) {
        this.format = format;
        this.oldBorderType = oldBorderType;
        this.newBorderType = newBorderType;
    }

    @Override
    public void execute() {
      //  format.setBorderStyle(newBorderType);
    }

    @Override
    public void undo() {
      //  format.setBorderStyle(oldBorderType);
    }
}