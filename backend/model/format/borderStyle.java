package backend.model.format;

public enum borderStyle {
    SOLID(),

    PIXELATED(){
        @Override
        public String toString() {
            return "Pixeleado";
        }
    },
    DASHED_FINE(){
        @Override
        public String toString() {
            return "Punteado Simple";
        }},
    DASHED_COMPLEX(){
        @Override
                public String toString(){
            return "Punteado Complex";
        }
    };

    public String getDisplayName() {
        return "Normal";
    }
}
