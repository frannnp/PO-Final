package backend.actions;

import backend.model.Figure;
import javafx.scene.paint.Color;
public class ChangeGradientColor implements Action{
        private final Figure figure;
        private final Color oldColor;
        private final Color newColor;

        public ChangeGradientColor(Figure figure, Color oldColor, Color newColor) {
            this.figure = figure;
            this.oldColor = oldColor;
            this.newColor = newColor;
        }

        @Override
        public void execute() {
       //     figure.setSecondaryFillColor(newColor);
        }

        @Override
        public void undo() {
        //    figure.setSecondaryFillColor(oldColor);
        }
}
