package backend.format;

import frontend.BorderStyle;
import frontend.ShadowStyle;


public class FigureFormatData {
    private ColorData fillColor;
    private ColorData gradientColor;
    private ColorData lineColor;
    private BorderStyle borderStyle;
    private ShadowStyle shadowStyle;
    private boolean useGradient;
    public FigureFormatData(ColorData fillColor, ColorData gradientColor, ColorData lineColor, BorderStyle border, ShadowStyle shadow) {
        this.fillColor = fillColor;
        this.gradientColor = gradientColor;
        this.lineColor = lineColor;
        this.borderStyle = border;
        this.shadowStyle = shadow;
        this.useGradient = gradientColor != null;
    }

    public ColorData getFillColor() {
        return fillColor;
    }
    public void setFillColor(ColorData fillColor) {
        this.fillColor = fillColor;
    }

    public ColorData getGradientColor() {
        return gradientColor;
    }
    public void setGradientColor(ColorData gradientColor) {
        this.gradientColor = gradientColor;
    }

    public ColorData getLineColor() {
        return lineColor;
    }
    public void setLineColor(ColorData lineColor) {
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
    public boolean isUseGradient() {
        return useGradient;
    }
    public void setUseGradient(boolean useGradient) {
        this.useGradient = useGradient;
    }

}
