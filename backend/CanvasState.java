package backend;

import backend.actions.Action;

import backend.actions.ActionHistory;
import backend.model.effects.EffectType;
import backend.model.format.ColorData;
import backend.model.format.FigureFormatData;
import backend.model.figures.Figure;
import backend.model.figures.Point;
import backend.model.format.borderStyle;
import backend.model.layer.Layer;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CanvasState implements Iterable<Figure> {

    private final LinkedList<Layer> layers = new LinkedList<>();
    private Layer currentLayer;
    private final ActionHistory actionHistory = new ActionHistory();

    private FigureFormatData currentFormat;
    private FigureFormatData copiedFormat;

    public CanvasState() {
        for (int i = 1; i <= 3; i++) {
            layers.add(new Layer("Capa " + i));
        }
        currentLayer = layers.getFirst();
    }
    public List<String> getLayerNames() {
        return layers.stream()
                .map(l -> l.name)
                .toList();
    }
    public void addLayer() {
        int max = layers.size() + 1;
        Layer L = new Layer("Capa " + max);
        layers.addLast(L);
        currentLayer = L;
    }
    public void removeLayer(String name) {
        Layer toRemove = null;
        for (Layer L : layers) {
            if (L.name.equals(name)) {
                toRemove = L;
                break;
            }
        }
        if (toRemove == null || layers.indexOf(toRemove) < 3 || toRemove.locked) {
            return;
        }
        layers.remove(toRemove);
        if (currentLayer == toRemove) currentLayer = layers.getFirst();
    }
    public void setCurrentLayer(String name) {
        for (Layer L : layers) {
            if (L.name.equals(name)) {
                currentLayer = L;
                return;
            }
        }
    }
    public void setLayerVisible(String name, boolean visible) {
        for (Layer L : layers) {
            if (L.name.equals(name)) {
                L.visible = visible;
                return;
            }
        }
    }
    public void setLayerLocked(String name, boolean locked) {
        for (Layer L : layers) {
            if (L.name.equals(name)) {
                L.locked = locked;
                return;
            }
        }
    }
    public void addFigure(Figure figure) {
        if (!currentLayer.locked) {
            currentLayer.figures.add(figure);
        };
    }

    public void deleteFigure(Figure figure) {
        for (Layer L : layers) {
            if (L.figures.remove(figure)) return;
        };
    }

    public Figure findTopFigureInPoint(Point p){
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer L = layers.get(i);
            if (!L.visible) continue;
            Iterator<Figure> it = L.figures.descendingIterator();
            while (it.hasNext()) {
                Figure f = it.next();
                if (f.belongs(p)) return f;
            }
        }
        return null;
    }

    public Iterator<Figure> figures() {
        return layers.stream()
                .flatMap(L -> L.visible ? L.figures.stream() : Stream.<Figure>empty())
                .iterator();    }

    public void sendToTop(Figure figure) {
        for (Layer L : layers) {
            if (L.figures.remove(figure)) {
                L.figures.addFirst(figure);
                return;
            }
        }
    }
    public void sendToBottom(Figure figure) {
        for (Layer L : layers) {
            if (L.figures.remove(figure)) {
                L.figures.addLast(figure);
                return;
            }
        }
    }
    @Override
    public Iterator<Figure> iterator() {
        return figures();
    }

    public void executeAction(Action action) {
        actionHistory.execute(action);
    }
    public void undo(){
        actionHistory.undo();
    }
    public void redo(){
        actionHistory.redo();
    }
    public boolean canUndo(){
        return actionHistory.canUndo();
    }
    public boolean canRedo(){
        return actionHistory.canRedo();
    }

    public void moveFigure(Figure f, double dx, double dy) {
        f.move(dx, dy);
    }
    public void addEffect(Figure f, EffectType effect) {
        f.addEffect(effect);
    }
    public void removeEffect(Figure f, EffectType effect) {
        f.removeEffect(effect);
    }
    public void setBorder(Figure f, borderStyle border) {
        f.setBorder(border);
    }
    public void setFillColor(Figure f, ColorData color) {
            f.setFillColor(color);
    }
    public void copyFormat(Figure f) {
            this.copiedFormat = f.getFormat();
    }
    public void pasteFormat(Figure f) {
        f.setFormat(this.copiedFormat);
    }
    public FigureFormatData getCurrentFormat() {
        return currentFormat;
    }
    public void setCurrentFormat(FigureFormatData currentFormat) {
        this.currentFormat = currentFormat;
    }

    public boolean isLayerLocked(String name) {
        for (Layer L : layers) {
            if (L.name.equals(name) && L.locked) return true;
    }            return false;
    };

    public boolean isLayerVisible(String name) {
        for (Layer L : layers) {
            if (L.name.equals(name) && L.visible) return true;
        }            return false;
    };
}
