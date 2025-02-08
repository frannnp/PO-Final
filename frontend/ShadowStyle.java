package frontend;

import javafx.scene.canvas.GraphicsContext;

public enum ShadowStyle {
    NONE("Sin Sombra"), SIMPLE("Sombra simple"), COLORED("Sombra Coloreada"), MULTIPLE("Sombra multiple");

    private String name;
    ShadowStyle(String name) {
        this.name = name;
    }
    public void drawShadow(GraphicsContext gc) {

    }
}

