package frontend;

import backend.CanvasState;
import backend.actions.AddFigure;
import backend.model.*;
import frontend.drawers.*;
import frontend.factory.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
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


	Color defaultLineColor = Color.BLACK;
	Color defaultFillColor = Color.YELLOW;


	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	ToggleButton deleteButton = new ToggleButton("Borrar");

	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");

	ToggleButton undoButton = new ToggleButton("Undo");
	ToggleButton redoButton = new ToggleButton("Redo");

	ToggleButton bringToFrontButton = new ToggleButton("Mandar al frente");
	ToggleButton sendToBackButton = new ToggleButton("Enviar al fondo");

	ChoiceBox<BorderStyle> borderChoice = new ChoiceBox<>();
	//todo
	//Choicebox<ShadowStyle> shadowChoice = new ChoiceBox<>();

	ChoiceBox<String> layerChoiceBox = new ChoiceBox<>();
	Button addLayerButton = new Button("Agregar Capa");
	Button deleteLayerButton = new Button("Eliminar Capa");
	ToggleGroup layerVisibilityGroup = new ToggleGroup();
	RadioButton showLayerButton = new RadioButton("Mostrar");
	RadioButton hideLayerButton = new RadioButton("Ocultar");
	CheckBox lockLayerCheckBox = new CheckBox("Bloquear");

	// Selector de color de relleno
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);
	ColorPicker gradientColorPicker = new ColorPicker(defaultFillColor);
	ColorPicker lineColorPicker = new ColorPicker(defaultLineColor);

	// Dibujar una figura
	Point startPoint;

	// Seleccionar una figura
	Figure selectedFigure;

	ToggleButton selectedFigureButton;

	// StatusBar
	StatusPane statusPane;

	// Colores de relleno de cada figura
	Map<Figure, Color> figureColorMap = new HashMap<>();
	Map<ToggleButton, FigureFactory> figureFactoryMap = new HashMap<>();
	Map<Figure, FigureFormat> figureFormatMap = new HashMap<>();
	Map<Figure, FigureDrawer> figureDrawerMap = new HashMap<>();
	Map<ToggleButton, FigureDrawer> buttonDrawerMap = new HashMap<>();

	private ToggleButton getSelectedFigureButton() {
		for(ToggleButton tool : toolsArr){
			if(tool.isSelected() && figureFactoryMap.containsKey(tool)) {
				return tool;
			}
		}
		return null;
	}
	ToggleButton[] toolsArr = {selectionButton,rectangleButton, circleButton, squareButton, ellipseButton, deleteButton, undoButton, redoButton};

	public PaintPane(CanvasState canvasState, StatusPane statusPane) {
		this.canvasState = canvasState;
		this.statusPane = statusPane;

		ToggleGroup tools = new ToggleGroup();
		for (ToggleButton tool : toolsArr) {
			tool.setMinWidth(90);
			tool.setToggleGroup(tools);
			tool.setCursor(Cursor.HAND);
		}


		figureFactoryMap.put(rectangleButton, new RectangleFactory(this,canvasState));
		figureFactoryMap.put(ellipseButton, new EllipseFactory(this,canvasState));
		figureFactoryMap.put(circleButton,new CircleFactory(this,canvasState));
		figureFactoryMap.put(squareButton,new SquareFactory(this,canvasState));

		buttonDrawerMap.put(rectangleButton, new RectangleDrawer());
		buttonDrawerMap.put(ellipseButton, new EllipseDrawer());
		buttonDrawerMap.put(circleButton, new CircleDrawer());
		buttonDrawerMap.put(squareButton, new SquareDrawer());

		VBox buttonsBox = new VBox(10);
		buttonsBox.getChildren().addAll(toolsArr);
		buttonsBox.getChildren().add(fillColorPicker);
		buttonsBox.getChildren().add(gradientColorPicker);
		buttonsBox.getChildren().add(lineColorPicker);

		buttonsBox.getChildren().add(bringToFrontButton);
		buttonsBox.getChildren().add(sendToBackButton);

		buttonsBox.getChildren().add(borderChoice);
		//todo
		//buttonsBox.getChildren().add(shadowChoice);

		buttonsBox.getChildren().add(layerChoiceBox);
		buttonsBox.getChildren().add(addLayerButton);
		buttonsBox.getChildren().add(deleteLayerButton);
		buttonsBox.getChildren().add(showLayerButton);
		buttonsBox.getChildren().add(hideLayerButton);
		buttonsBox.getChildren().add(lockLayerCheckBox);

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


		sendToBackButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.sendToBottom(selectedFigure);
				redrawCanvas();
			}
		});
		borderChoice.setOnAction(event ->{
			if(selectedFigure != null){
				figureFormatMap.get(selectedFigure).setBorderStyle(borderChoice.getValue());
			}
		});
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
			Figure figure = canvasState.findTopFigureInPoint(new Point(event.getX(), event.getY()));
			if(figure == null){
				statusPane.updateStatus("Ninguna figura encontrada");
			}
			else {
				statusPane.updateStatus(String.format("Se seleccionó: %s",figure));
			}
			selectedFigure = figure;
			redrawCanvas();
		}
	}

	private void onMouseMoved(MouseEvent event) {
		Point eventPoint = new Point(event.getX(), event.getY());
		Figure figure = canvasState.findTopFigureInPoint(new Point(event.getX(), event.getY()));
		if(figure != null){
			statusPane.updateStatus(figure.toString());
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
				gc.setStroke(figureFormatMap.get(figure).getLineColor());
			}
			gc.setFill(figureColorMap.get(figure));
			figureDrawerMap.get(figure).draw(gc,figureFormatMap.get(figure),figure);
		}
	}

		private void onMouseReleased(MouseEvent event) {
		Point endPoint = new Point(event.getX(), event.getY());
		if(startPoint == null) {
			return ;
		}
		if(endPoint.getX() < startPoint.getX() || endPoint.getY() < startPoint.getY()) {
			return ;
		}
		ToggleButton button = getSelectedFigureButton();
		if(button == null)
			return;

		//FigureFormat format = new FigureFormat(Color.YELLOW,Color.ORANGE)
		Figure newFigure = figureFactoryMap.get(button).generateFigure(startPoint,endPoint);
		canvasState.executeAction(new AddFigure(canvasState,newFigure));

		figureColorMap.put(newFigure, fillColorPicker.getValue());
		figureFormatMap.put(newFigure, new FigureFormat(fillColorPicker.getValue(), gradientColorPicker.getValue(),lineColorPicker.getValue(),borderChoice.getValue(),ShadowStyle.NONE));
		figureDrawerMap.put(newFigure, buttonDrawerMap.get(getSelectedFigureButton()));
		canvasState.addFigure(newFigure);
		startPoint = null;
		redrawCanvas();
	}

	private void onMousePressed(MouseEvent event) {
		startPoint = new Point(event.getX(), event.getY());
	}
}
