package backend.actions.format;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;
import backend.model.format.FigureFormatData;
import frontend.FigureFormat;

public class PasteFormat implements Action {
    private final CanvasState canvas;
    private final Figure figure;
    private final FigureFormatData oldFormat;
    private final FigureFormatData newFormat;

    public PasteFormat(CanvasState canvas, Figure figure) {
        this.canvas = canvas;
        this.figure = figure;
        this.oldFormat = figure.getFormat().copy();
        this.newFormat = canvas.getCurrentFormat().copy();
    }
    @Override
    public void execute() {
        canvas.pasteFormat(figure);
    }

    @Override
    public void undo() {
        figure.setFormat(oldFormat);
    }
}
