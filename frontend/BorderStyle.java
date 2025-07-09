package frontend;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.StrokeLineCap;

public enum BorderStyle{
        SOLID {
            @Override
            public void apply(GraphicsContext gc) {
                gc.setLineWidth(1);
                gc.setLineCap(StrokeLineCap.BUTT);
                gc.setLineDashes();  // línea continua
            }
        },
        PIXELATED {
            @Override
            public void apply(GraphicsContext gc) {
                gc.setLineWidth(5);
                gc.setLineCap(StrokeLineCap.BUTT);
                gc.setLineDashes(1, 1);
            }
        },
        DASHED_FINE {
            @Override
            public void apply(GraphicsContext gc) {
                gc.setLineWidth(1);
                gc.setLineCap(StrokeLineCap.ROUND);
                gc.setLineDashes(2, 6);
            }
        },
        DASHED_COMPLEX {
            @Override
            public void apply(GraphicsContext gc) {
                gc.setLineWidth(3);
                gc.setLineCap(StrokeLineCap.SQUARE);
                gc.setLineDashes(25, 10, 15, 10);
            }
        };
    public abstract void apply(GraphicsContext gc);
    public void clear(GraphicsContext gc) {
        gc.setLineDashes();
    }
}
