package frontend.drawers;

import backend.model.effects.EffectType;
import backend.model.figures.Figure;
import frontend.FigureFormat;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.EnumSet;

public abstract class FigureDrawer {
    public void predraw(GraphicsContext gc, FigureFormat format, Figure figure){
        gc.setFill(format.getFillColor());
    }
    public void postdraw(GraphicsContext gc, FigureFormat format, Figure figure){
        format.getBorderStyle().clear(gc);
    }
    public void draw(GraphicsContext gc, FigureFormat format, Figure figure, EnumSet<EffectType> effects) {
        predraw(gc, format, figure);
        drawShape(gc, figure);

        if(effects.contains(EffectType.ACTIVE)) {
            if (effects.contains(EffectType.MIRROR_HORIZONTAL)) drawHorizontalMirror(gc, figure);
            if (effects.contains(EffectType.MIRROR_VERTICAL)) drawVerticalMirror(gc, figure);


            if (effects.contains(EffectType.LIGHTEN)) applyLighten(gc, figure);
            if (effects.contains(EffectType.DARKEN)) applyDarken(gc, figure);
        }


        postdraw(gc, format, figure);
    }

    private void drawShape(GraphicsContext gc, Figure figure){
        drawFill(gc, figure);
        drawBorder(gc, figure);
    }
    protected abstract void drawBorder(GraphicsContext gc, Figure figure);
    protected abstract void drawFill(GraphicsContext gc, Figure figure); // fill only

    private void applyLighten(GraphicsContext gc, Figure figure) {
        gc.setFill(Color.rgb(255,255,255,0.7));
        drawFill(gc, figure);
    }
    private void applyDarken(GraphicsContext gc, Figure figure) {
        gc.setFill(Color.rgb(0,0,0,0.3));
        drawFill(gc, figure);
    }
    protected abstract void drawHorizontalMirror(GraphicsContext gc, Figure figure);
    protected abstract void drawVerticalMirror (GraphicsContext gc, Figure figure);
}
