package backend.model

        public abstract class AbstractFigure implements Figure {
            private final String name;
            protected AbstractFigure(String name) {
                this.name = name;
            }
            public String getName() {
                return name;
            }
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getFormat(););
            }
            protected abstract String getFormat();
        }