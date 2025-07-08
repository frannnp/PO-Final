package frontend;
import backend.format.ColorData;
import backend.format.FigureFormatData;
import javafx.scene.paint.Color;

public class FormatMapper {
    public static FigureFormat toFxFormat(FigureFormatData data) {
        Color fill   = toFxColor(data.getFillColor());
        Color line    = toFxColor(data.getLineColor());
        return new FigureFormat(fill,line);
    }
    private static Color toFxColor(ColorData c) {
        if (c == null) return null;
        return new Color(
                c.getRed()   / 255.0,
                c.getGreen() / 255.0,
                c.getBlue()  / 255.0,
                c.getAlpha()
        );
    }

    public static ColorData toColorData(Color fx) {
        return new ColorData(
                (int)(fx.getRed()   * 255),
                (int)(fx.getGreen() * 255),
                (int)(fx.getBlue()  * 255),
                fx.getOpacity()
        );
    }

}
