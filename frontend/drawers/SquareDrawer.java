package frontend.drawers;

import backend.model.Rectangle;
import backend.model.Square;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;

public class SquareDrawer extends RectangleDrawer{


    public SquareDrawer(Square square) {
        super(square);
    }
}
