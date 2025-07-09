package backend.model.figures;

import backend.Movable;
import backend.effects.EffectType;
import backend.format.FigureFormatData;


import java.util.EnumSet;
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
    public  Figure createHorizontalMirror(){
        return copyScaled(-1,1);
    }
    public Figure createVerticalMirror(){
        return copyScaled(1, -1);
    }

    private final Set<EffectType> effects = EnumSet.noneOf(EffectType.class);

    public Set<EffectType> getEffects() {
        return effects;
    }
    public void addEffect(EffectType effect) {
        effects.add(effect);
    }

    public void removeEffect(EffectType effect) {
        effects.remove(effect);
    }

    public void toggleEffect(EffectType effect) {
        if (effects.contains(effect)) effects.remove(effect);
        else effects.add(effect);
    }


    public boolean hasEffect(EffectType effect) {
        return effects.contains(effect);
    }

}