package backend.model;

import backend.Movable;
import frontend.MainFrame;
import javafx.scene.canvas.GraphicsContext;

public abstract class Figure implements Movable {
            private MainFrame.FigureFormat format;
            public abstract boolean belongs(Point p);
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getParameters());
            }
            public abstract String getParameters();
            public abstract String getName();

            public MainFrame.FigureFormat getFormat(){
                return format;
            }
        }