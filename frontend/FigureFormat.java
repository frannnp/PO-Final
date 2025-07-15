package frontend;

import backend.model.format.FigureFormatData;
import javafx.scene.paint.Color;

import static frontend.FormatMapper.toFx;

public class FigureFormat {
    private final Color fillColor;
    private final BorderStyle borderStyle;
    public FigureFormat(FigureFormatData d) {
        this.fillColor = toFx(d.getFillColor());
        this.borderStyle = FormatMapper.toFx(d.getBorderStyle());
    }

    public FigureFormat(Color fill, BorderStyle b) {
        this.fillColor = fill;
        this.borderStyle = b;
    }

    public Color getFillColor() {
        return fillColor;
    }
    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public FigureFormatData toData() {
        return FormatMapper.toData(this);
    }
}
