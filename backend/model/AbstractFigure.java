package backend.model;

        public abstract class AbstractFigure implements Figure {
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getFormat());
            }
        }