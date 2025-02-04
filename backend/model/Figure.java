package backend.model;

import backend.Movable;

public abstract class Figure implements Movable {
            public abstract boolean belongs(Point p);
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getFormat());
            }
            public abstract String getFormat();
            public abstract String getName();
        }