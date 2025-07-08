package backend.model.figures;

import backend.Movable;
import backend.effects.EffectType;
import backend.format.FigureFormatData;


import java.util.EnumSet;
import java.util.Set;

public abstract class Figure implements Movable {
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