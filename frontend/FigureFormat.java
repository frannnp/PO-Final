package frontend;

import backend.format.FigureFormatData;
import javafx.scene.paint.Color;

import static frontend.FormatMapper.toFxBorder;
import static frontend.FormatMapper.toFxColor;

public class FigureFormat {
    private Color fillColor;
    private BorderStyle borderStyle;
    public FigureFormat(FigureFormatData d) {
        this.fillColor = toFxColor(d.getFillColor());
        this.borderStyle = toFxBorder(d.getBorderStyle());
    }

    public Color getFillColor() {
        return fillColor;
    }
    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

}
