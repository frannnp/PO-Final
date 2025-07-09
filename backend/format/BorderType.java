package backend.format;

public enum BorderType {
    SOLID("Normal"),
    PIXELATED("Pixeleado"),
    DASHED_FINE("Punteado Simple"),
    DASHED_COMPLEX("Ounteado Complejo");

    private final String displayName;

    BorderType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
