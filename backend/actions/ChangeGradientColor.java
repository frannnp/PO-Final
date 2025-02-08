package backend.actions;

import backend.model.Figure;
import frontend.FigureFormat;
import javafx.scene.paint.Color;
public class ChangeGradientColor implements Action{
        private final FigureFormat format;
        private final Color oldColor;
        private final Color newColor;

        public ChangeGradientColor(FigureFormat format, Color oldColor, Color newColor) {
            this.format = format;
            this.oldColor = oldColor;
            this.newColor = newColor;
        }

        @Override
        public void execute() {
            format.setGradientColor(newColor);
        }

        @Override
        public void undo() {
            format.setGradientColor(oldColor);
        }
}
