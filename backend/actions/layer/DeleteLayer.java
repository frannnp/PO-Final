package backend.actions.layer;

import backend.CanvasState;
import backend.model.layer.Layer;

public class DeleteLayer extends AddLayer{
    public DeleteLayer(CanvasState canvas, Layer layer) {
        super(canvas, layer);
    }
    @Override
    public void execute() {
        super.undo();
    }

    @Override
    public void undo() {
        super.execute();
    }

}
