package backend.model;

        public abstract class Figure {
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getFormat());
            }
            public abstract String getFormat();
            public abstract String getName();
        }