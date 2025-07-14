package backend.actions.format;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.format.FigureFormatData;

public class UpdateFormat implements Action {
    private final CanvasState canvas;
    private final FigureFormatData format;
    private final FigureFormatData formatOld;

    public UpdateFormat(FigureFormatData d, CanvasState canvas) {
        this.format = d;
        this.canvas = canvas;
        this.formatOld = format;
    }
    @Override
    public void execute() {
    }

    @Override
    public void undo() {

    }
}
