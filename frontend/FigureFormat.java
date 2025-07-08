package frontend;

import javafx.scene.paint.Color;

public class FigureFormat {
    private Color fillColor;
    private Color lineColor;
    private BorderStyle borderStyle;
    public FigureFormat(Color fillColor, Color lineColor, BorderStyle border) {
        this.fillColor = fillColor;
        this.lineColor = lineColor;
        this.borderStyle = border;
    }

    public Color getFillColor() {
        return fillColor;
    }
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
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

    public FigureFormat copy() {
        FigureFormat copy = new FigureFormat(fillColor, lineColor, borderStyle);
        return copy;
    }
}
