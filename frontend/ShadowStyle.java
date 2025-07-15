package frontend;

public enum ShadowStyle {
    NONE("Sin Sombra"), SIMPLE("Sombra simple"), COLORED("Sombra Coloreada"), MULTIPLE("Sombra multiple");

    private final String name;
    ShadowStyle(String name) {
        this.name = name;
    }
}

