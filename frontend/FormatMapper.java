package frontend;
import backend.format.borderStyle;
import backend.format.ColorData;
import javafx.scene.paint.Color;

public class FormatMapper {

    public static BorderStyle toFxBorder(borderStyle style) {
        switch(style) {
            case SOLID:          return BorderStyle.SOLID;
            case PIXELATED:      return BorderStyle.PIXELATED;
            case DASHED_FINE:    return BorderStyle.DASHED_FINE;
            case DASHED_COMPLEX: return BorderStyle.DASHED_COMPLEX;
            default:             throw new IllegalArgumentException(style.name());
        }
    }
    public static borderStyle toBorderData(BorderStyle fx) {
        switch(fx) {
            case SOLID:          return borderStyle.SOLID;
            case PIXELATED:      return borderStyle.PIXELATED;
            case DASHED_FINE:    return borderStyle.DASHED_FINE;
            case DASHED_COMPLEX: return borderStyle.DASHED_COMPLEX;
            default:             throw new IllegalArgumentException(fx.name());
        }
    }

    public static Color toFxColor(ColorData c) {
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
