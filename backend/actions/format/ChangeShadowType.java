package backend.actions.format;

import backend.actions.Action;
import frontend.FigureFormat;
import frontend.ShadowStyle;

public class ChangeShadowType implements Action {
    private final FigureFormat format;
    private final ShadowStyle oldShadowStyle;
    private final ShadowStyle newShadowType;

    public ChangeShadowType(FigureFormat format, ShadowStyle oldShadowType, ShadowStyle newShadowType) {
        this.format = format;
        this.oldShadowStyle = oldShadowType;
        this.newShadowType = newShadowType;
    }

    @Override
    public void execute() {
        format.setShadowStyle(newShadowType);
    }

    @Override
    public void undo() {
        format.setShadowStyle(oldShadowStyle);
    }}
