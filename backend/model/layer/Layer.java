package backend.model.layer;

import backend.model.figures.Figure;

import java.util.LinkedList;

public class Layer {
    public final String name;
    public boolean visible = true;
    public boolean locked  = false;
    public final LinkedList<Figure> figures = new LinkedList<>();
    public Layer(String name) { this.name = name; }
}
