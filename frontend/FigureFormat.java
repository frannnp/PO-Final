package frontend;

import javafx.scene.paint.Color;

public class FigureFormat {
    private Color fillColor;
    private Color gradientColor;
    private Color lineColor;
    private BorderStyle borderStyle;
    private boolean useGradient;
    public FigureFormat(Color fillColor, Color gradientColor, Color lineColor, BorderStyle border) {
        this.fillColor = fillColor;
        this.gradientColor = gradientColor;
        this.lineColor = lineColor;
        this.borderStyle = border;
        this.useGradient = gradientColor != null;
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


    public BorderStyle getBorderStyle() {
        return borderStyle;
    }
    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }
    public boolean isUseGradient() {
        return useGradient;
    }
    public void setUseGradient(boolean useGradient) {
        this.useGradient = useGradient;
    }

    public FigureFormat copy() {
        FigureFormat copy = new FigureFormat(fillColor, gradientColor, lineColor, borderStyle);
        copy.setUseGradient(useGradient);
        return copy;
    }
}
