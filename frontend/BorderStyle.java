package frontend;

import javafx.scene.canvas.GraphicsContext;

public enum BorderStyle{
    NORMAL("Normal"),
    SIMPLE_DOTTED("Punteado Simple"),
    COMPLEX_DOTTED("Ounteado Complejo");

    private String name;
    BorderStyle(String name){
        this.name = name;
    }
    public void setBorder(GraphicsContext gc){
        switch(this){
            default ->
                gc.setLineDashes(0);
            case SIMPLE_DOTTED ->
                gc.setLineDashes(10);
            case COMPLEX_DOTTED ->
                gc.setLineDashes(30,10,15,10);
        }
    }
}
