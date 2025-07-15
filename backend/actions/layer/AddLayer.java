package backend.actions.layer;

import backend.CanvasState;
import backend.actions.Action;
import backend.model.layer.Layer;

public class AddLayer implements Action {
    private final CanvasState canvas;
    private final Layer layer;

    public AddLayer(CanvasState canvas, Layer layer) {
        this.canvas = canvas;
        this.layer= layer;
    }
    @Override
    public void execute() {
        canvas.addLayer();
    }

    @Override
    public void undo() {
        canvas.removeLayer(layer.name);
    }
}
