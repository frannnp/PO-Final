package backend.model.figures;

import backend.model.effects.EffectType;
import backend.model.format.ColorData;
import backend.model.format.FigureFormatData;
import backend.model.format.borderStyle;


import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public abstract class Figure implements Movable , Cloneable {
            private FigureFormatData format;
            public abstract boolean belongs(Point p);
            @Override
            public String toString() {
                return String.format("%s [ %s ]", getName(), getParameters());
            }
            public abstract String getParameters();
            public abstract String getName();

            public FigureFormatData getFormat(){
                return format;
            }
            public void setFormat(FigureFormatData format){
                this.format = format;
            }

    @Override
    public Figure clone() {
        try {
            Figure copy = (Figure) super.clone();
            if (this.format != null) {
                copy.format.setFormat(this.format);
            }
            copy.effects.clear();
            copy.effects.addAll(this.effects);
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public Figure copyWithOffset(double dx, double dy) {
        Figure copy = clone();
        copy.move(dx, dy);
        return copy;
    }
    public abstract Figure copyScaled(double scaleX, double scaleY);

    public abstract List<Figure> divideHorizontally(int parts);
    public abstract List<Figure> divideVertically(int parts);


    private final EnumSet<EffectType> effects = EnumSet.noneOf(EffectType.class);

    public EnumSet<EffectType> getEffects() {
        return effects;
    }
    public void addEffect(EffectType effect) {
        effects.add(effect);
    }

    public void removeEffect(EffectType effect) {
        effects.remove(effect);
    }
    public void setFillColor(ColorData c){
        format.setFillColor(c);
    }
    public void setBorder(borderStyle b){
        format.setBorderStyle(b);
    }
    public void setEffects(EnumSet<EffectType> e){
        format.setEffects(e);
    }

}