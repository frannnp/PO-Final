package backend.actions.format;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.figures.Figure;
import backend.model.format.FigureFormatData;

public class CopyFormat implements Action {//TODO
    private final CanvasState canvas;
    private final Figure figure;
    private final FigureFormatData oldFormat;


    public CopyFormat(CanvasState canvas, Figure figure) {
        this.canvas = canvas;
        this.figure = figure;
        this.oldFormat = figure.getFormat().copy();
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
