package backend.format;


public class FigureFormatData {
    private ColorData fillColor;
    private ColorData lineColor;
    private BorderType borderStyle;
    public FigureFormatData(ColorData fillColor, ColorData lineColor, BorderType border) {
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

    public BorderType getBorderStyle() {
        return borderStyle;
    }
    public void setBorderStyle(BorderType borderStyle) {
        this.borderStyle = borderStyle;
    }

}
