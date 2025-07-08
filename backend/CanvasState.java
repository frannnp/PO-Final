package backend;

import backend.actions.Action;
import backend.model.figures.Figure;
import backend.model.figures.Point;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CanvasState implements Iterable<Figure> {

    private final List<Figure> figures = new LinkedList<>();
    private final ActionHistory actionHistory = new ActionHistory();

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

}
