package backend;

import backend.actions.Action;

import backend.actions.ActionHistory;
import backend.model.effects.EffectType;
import backend.model.format.ColorData;
import backend.model.format.FigureFormatData;
import backend.model.figures.Figure;
import backend.model.figures.Point;
import backend.model.format.borderStyle;


import java.util.Iterator;
import java.util.LinkedList;

public class CanvasState implements Iterable<Figure> {

    private final LinkedList<Figure> figures = new LinkedList<>();
    private final ActionHistory actionHistory = new ActionHistory();

    private FigureFormatData currentFormat;
    private FigureFormatData copiedFormat;

    public void addFigure(Figure figure) {
        figures.add(figure);
    }

    public void deleteFigure(Figure figure) {
        figures.remove(figure);
    }

    public Figure findTopFigureInPoint(Point p){
        for(Figure figure : figures){
            if(figure.belongs(p))
                return figure;
        }
        return null;
    }

    public Iterable<Figure> figures() {
        return new LinkedList<>(figures);
    }

    public void sendToTop(Figure figure) {
            if(figures.contains(figure)) {
                figures.remove(figure);
                figures.addFirst(figure);
            }
    }
    public void sendToBottom(Figure figure) {
        if(figures.contains(figure)) {
            figures.remove(figure);
            figures.addLast(figure);
        }
    }

    @Override
    public Iterator<Figure> iterator() {
        return figures.iterator();
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
    public void changeBorder(Figure f, borderStyle border) {
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

}
