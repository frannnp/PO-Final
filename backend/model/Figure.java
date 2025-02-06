package backend.model;

import backend.Movable;
import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Movable {
            public abstract boolean belongs(Point p);
            public abstract void draw(GraphicsContext gc);
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getFormat());
            }
            public abstract String getFormat();
            public abstract String getName();
        }