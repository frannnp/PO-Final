package backend.model.effects;

public enum EffectType {//todo
    ACTIVE("Toggle"),
    LIGHTEN("Lighten"),
    DARKEN("Darken"),
    MIRROR_HORIZONTAL("Horizontal Mirror"),
    MIRROR_VERTICAL("Vertical Mirror");

    private final String displayName;

    EffectType(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
}
