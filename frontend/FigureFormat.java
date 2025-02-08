package frontend;

import javafx.scene.paint.Color;

public class FigureFormat {
    private Color fillColor;
    private Color gradientColor;
    private Color lineColor;
    private BorderStyle borderStyle;
    private ShadowStyle shadowStyle;

    public FigureFormat(Color fillColor, Color gradientColor, Color lineColor, BorderStyle border, ShadowStyle shadow) {
        this.fillColor = fillColor;
        this.gradientColor = gradientColor;
        this.lineColor = lineColor;
        this.borderStyle = border;
        this.shadowStyle = shadow;
    }

    public Color getFillColor() {
        return fillColor;
    }
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }
    public Color getGradientColor() {
        return gradientColor;
    }
    public void setGradientColor(Color gradientColor) {
        this.gradientColor = gradientColor;
    }
    public Color getLineColor() {
        return lineColor;
    }
    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
    public ShadowStyle getShadowStyle() {
        return shadowStyle;
    }
    public void setShadowStyle(ShadowStyle shadowStyle) {
        this.shadowStyle = shadowStyle;
    }
    public BorderStyle getBorderStyle() {
        return borderStyle;
    }
    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }
}
