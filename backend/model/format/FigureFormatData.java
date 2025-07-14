package backend.model.format;


import backend.model.effects.EffectType;

import java.util.EnumSet;

public class FigureFormatData {
    private ColorData fillColor;
    private borderStyle borderStyle;
    private EnumSet<EffectType> effects;
    public FigureFormatData(ColorData fillColor, borderStyle border) {
        this.fillColor = fillColor;
        this.borderStyle = border;
    }

    public ColorData getFillColor() {
        return fillColor;
    }
    public void setFillColor(ColorData fillColor) {
        this.fillColor = fillColor;
    }

    public borderStyle getBorderStyle() {
        return borderStyle;
    }
    public void setBorderStyle(borderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public EnumSet<EffectType> getEffects() {return effects;
    }
    public void setEffects(EnumSet<EffectType> effects) {
        this.effects = effects;
    }

    public FigureFormatData copy() {
        return new FigureFormatData(this.fillColor, this.borderStyle);
    }
    public void setFormat(FigureFormatData format) {
        this.fillColor = format.fillColor;
        this.borderStyle = format.borderStyle;
        this.effects = format.effects;
    }
}
