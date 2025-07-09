package backend.actions.format;
import backend.actions.Action;
import backend.format.FigureFormatData;
import frontend.BorderStyle; //todo sacar

public class ChangeBorderStyle implements Action {
    private final FigureFormatData format;
    private final BorderStyle oldBorderType;
    private final BorderStyle newBorderType;

    public ChangeBorderStyle(FigureFormatData format, BorderStyle oldBorderType, BorderStyle newBorderType) {
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