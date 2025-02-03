package backend;

import backend.model.Figure;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CanvasState implements Iterable<Figure> {

    private final List<Figure> list = new LinkedList<>();

    public void addFigure(Figure figure) {
        list.add(figure);
    }

    public void deleteFigure(Figure figure) {
        list.remove(figure);
    }

    public Iterable<Figure> figures() {
        return new LinkedList<>(list);
    }

    public void sendToTop(Figure figure) {

    }

    @Override
    public Iterator<Figure> iterator() {
        return list.iterator();
    }

}
