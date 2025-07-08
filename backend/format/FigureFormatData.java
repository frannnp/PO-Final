package backend.format;

import frontend.BorderStyle;
import frontend.ShadowStyle;


public class FigureFormatData {
    private ColorData fillColor;
    private ColorData lineColor;
    private BorderStyle borderStyle;
    public FigureFormatData(ColorData fillColor, ColorData lineColor, BorderStyle border) {
        this.fillColor = fillColor;
        this.lineColor = lineColor;
        this.borderStyle = border;
    }

    public ColorData getFillColor() {
        return fillColor;
    }
    public void setFillColor(ColorData fillColor) {
        this.fillColor = fillColor;
    }

    public ColorData getLineColor() {
        return lineColor;
    }
    public void setLineColor(ColorData lineColor) {
        this.lineColor = lineColor;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }
    public void setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

}
