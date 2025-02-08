package frontend;

import backend.CanvasState;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainFrame extends VBox {

    public MainFrame(CanvasState canvasState) {
        getChildren().add(new AppMenuBar());
        StatusPane statusPane = new StatusPane();
        getChildren().add(new PaintPane(canvasState, statusPane));
        getChildren().add(statusPane);
    }

    public static class FigureFormat {
        private Color fillColor;
        private Color gradientColor;

        private ShadowStyle shadowType;
        private BorderStyle borderStyle;

        private boolean useGradient;

        public FigureFormat(Color fillColor, Color gradientColor, ShadowStyle shadowType, BorderStyle borderStyle) {
            this.fillColor = fillColor;
            this.gradientColor = gradientColor;
            this.shadowType = shadowType;
            this.borderStyle = borderStyle;
        }
        public Color getFillColor() {
            return fillColor;
        }
        public Color getGradientColor() {
            return gradientColor;
        }
        public BorderStyle getBorderStyle() {
            return borderStyle;
        }

        public ShadowStyle getShadowType() {
            return shadowType;
        }

        public void setFillColor(Color fillColor) {
            this.fillColor = fillColor;
        }
        public void setGradientColor(Color gradientColor) {
            this.gradientColor = gradientColor;
        }
        public FigureFormat copy(){
            return new FigureFormat(fillColor, gradientColor, shadowType, borderStyle);
        }
    }
}
