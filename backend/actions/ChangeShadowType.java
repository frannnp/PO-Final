package backend.actions;

import backend.model.Figure;

public class ChangeShadowType implements Action {
    private final Figure figure;
    private final String oldShadowType;
    private final String newShadowType;

    public ChangeShadowType(Figure figure, String oldShadowType, String newShadowType) {
        this.figure = figure;
        this.oldShadowType = oldShadowType;
        this.newShadowType = newShadowType;
    }

    @Override
    public void execute() {
        //figure.setShadowType(newShadowType);
    }

    @Override
    public void undo() {
        //figure.setShadowType(oldShadowType);
    }}
