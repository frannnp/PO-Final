package frontend;

import backend.CanvasState;
import backend.model.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
	CanvasState canvasState;

	// Canvas y relacionados
	Canvas canvas = new Canvas(800, 600);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	Color lineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;

	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton deleteButton = new ToggleButton("Borrar");
	//TODO hacer un iterator (que itere) para iterar todas las posibles figuras (usando getName() :) ))
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton ellipseButton = new ToggleButton("Elipse");

	ToggleButton backButton = new ToggleButton("Back");
	ToggleButton forwardButton = new ToggleButton("Forward");

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);
	ColorPicker secondColorPicker = new ColorPicker(defaultFillColor);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	// StatusBar
	StatusPane statusPane;

	// Colores de relleno de cada figura
	Map<Figure, Color> figureColorMap = new HashMap<>();

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;
		ToggleButton[] toolsArr = {selectionButton, rectangleButton, circleButton, squareButton, ellipseButton, deleteButton};
		//todo misma aca, algo que itere
		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}
		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.getChildren().add(secondColorPicker);
		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

		canvas.setOnMousePressed(this::onMousePressed);
		canvas.setOnMouseReleased(this::onMouseReleased);
		canvas.setOnMouseMoved(this::onMouseMoved);
		canvas.setOnMouseClicked(this::onMouseClicked);
		canvas.setOnMouseDragged(this::onMouseDragged);

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);
	}

	private void onMouseDragged(MouseEvent event) {
		if(selectionButton.isSelected() && selectedFigure != null) {
			double diffX = event.getX() - startPoint.getX() ;
			double diffY = event.getY() - startPoint.getY() ;
			redrawCanvas();
			startPoint.move(diffX, diffY);

		}
	}

	private void onMouseClicked(MouseEvent event) {
		if(selectionButton.isSelected()) {
			Point eventPoint = new Point(event.getX(), event.getY());
			boolean found = false;
			StringBuilder label = new StringBuilder("Se seleccionó: ");
			for (Figure figure : canvasState.figures()) {
				if(figureBelongs(figure, eventPoint)) {
					found = true;
					selectedFigure = figure;
					label.append(figure.toString());
				}
			}
			if (found) {
				statusPane.updateStatus(label.toString());
			} else {
				selectedFigure = null;
				statusPane.updateStatus("Ninguna figura encontrada");
			}
			redrawCanvas();
		}
	}

	private void onMouseMoved(MouseEvent event) {
		Point eventPoint = new Point(event.getX(), event.getY());
		boolean found = false;
		StringBuilder label = new StringBuilder();
		for(Figure figure : canvasState.figures()) {
			if(figureBelongs(figure, eventPoint)) {
				found = true;
				label.append(figure.toString());
			}
		}
		if(found) {
			statusPane.updateStatus(label.toString());
		} else {
			statusPane.updateStatus(eventPoint.toString());
		}
	}

	void redrawCanvas() {
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for(Figure figure : canvasState.figures()) {
			if(figure == selectedFigure) {
				gc.setStroke(Color.RED);
			} else {
				gc.setStroke(lineColor);
			}
			gc.setFill(figureColorMap.get(figure));
			figure.draw(gc);
		}
	}

		boolean figureBelongs(Figure figure, Point eventPoint) {
			return figure.belongs(eventPoint);
		}


		private void onMouseReleased(MouseEvent event) {
		Point endPoint = new Point(event.getX(), event.getY());
		if(startPoint == null) {
			return ;
		}
		if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
			return ;
		}
		Figure newFigure = null;
		if(rectangleButton.isSelected()) {
			newFigure = new Rectangle(startPoint, endPoint);
		}
		else if(circleButton.isSelected()) {
			double circleRadius = Math.abs(endPoint.getX() - startPoint.getX());
			newFigure = new Circle(startPoint, circleRadius);
		} else if(squareButton.isSelected()) {
			double size = Math.abs(endPoint.getX() - startPoint.getX());
			newFigure = new Square(startPoint, size);
		} else if(ellipseButton.isSelected()) {
			Point centerPoint = new Point(Math.abs(endPoint.x + startPoint.x) / 2, (Math.abs((endPoint.y + startPoint.y)) / 2));
			double sMayorAxis = Math.abs(endPoint.x - startPoint.x);
			double sMinorAxis = Math.abs(endPoint.y - startPoint.y);
			newFigure = new Ellipse(centerPoint, sMayorAxis, sMinorAxis);
		} else {
			return ;
		}
		figureColorMap.put(newFigure, fillColorPicker.getValue());
		canvasState.addFigure(newFigure);
		startPoint = null;
		redrawCanvas();
	}

	private void onMousePressed(MouseEvent event) {
		startPoint = new Point(event.getX(), event.getY());
	}
}
