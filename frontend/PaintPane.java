package frontend;

import backend.CanvasState;
import backend.actions.figure.AddFigure;
import backend.actions.operation.MoveFigure;
import backend.model.figures.Figure;
import backend.model.figures.Point;
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


	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	Button deleteButton = new Button("Borrar");

	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");

	ChoiceBox<BorderStyle> borderChoice = new ChoiceBox<>();

    Color defaultFillColor = Color.YELLOW;
	ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

    Button copyFormatButton = new Button("Copiar"); //todo
    Button pasteFormatButton = new Button("Pastar");

	StatusPane statusPane;

	Map<ToggleButton, FigureFactory> factoryMap = new HashMap<>();
	Map<Figure, FigureFormat> formatMap = new HashMap<>();
	Map<Class<? extends Figure>, FigureDrawer> drawerRegistry = new HashMap<>();

	private ToggleButton getSelectedFigureButton() {
		for(ToggleButton tool : toolsArr){
			if(tool.isSelected() && factoryMap.containsKey(tool)) {
				return tool;
			}
		}
		return null;
	}
	ToggleButton[] toolsArr = {selectionButton,rectangleButton, circleButton, squareButton, ellipseButton};



    private enum Mode {
        SELECT{
            @Override
            void onPressed(PaintPane pane, MouseEvent e){
                if(pane.hoveredFigure == null){
                    pane.statusPane.updateStatus("Ninguna figura encontrada");
                }
                else {
                    pane.statusPane.updateStatus(String.format("Se seleccionó: %s", pane.hoveredFigure));
                }
                pane.selectedFigure = pane.hoveredFigure;
                pane.redrawCanvas();

            }
            @Override
            void onDragged(PaintPane pane, MouseEvent e){
                if(pane.selectedFigure != null || pane.eventStart==null) return; //todo checquear si se puede draggear un mouse sin apretar
                double dx = e.getX() - pane.eventStart.getX() ;
                double dy = e.getY() - pane.eventStart.getY() ; //todo facilitar esto en el movefigure
                pane.canvasState.executeAction(
                        new MoveFigure(pane.canvasState, pane.selectedFigure, dx, dy)
                );
                //pane.canvasState.moveFigure(pane.selectedFigure, dx, dy);

                pane.redrawCanvas();
                pane.eventStart.move(dx, dy);
            }
            @Override
            void onReleased(PaintPane pane, MouseEvent e) {
            }
        },
        DRAW{
        @Override
            void onPressed(PaintPane pane, MouseEvent e){
        }
            @Override
            void onDragged(PaintPane pane, MouseEvent e){
                FigureFactory factory = pane.getActiveFactory();
                if (factory == null || pane.eventStart==null ) return;
                pane.previewFigure = factory.generateFigure(pane.eventStart, pane.eventCurrent);
                pane.redrawCanvas();
                pane.drawPreviewFigure();
            }
            @Override
            void onReleased(PaintPane pane, MouseEvent e) {
                if (pane.previewFigure == null) return;
                pane.canvasState.executeAction(new AddFigure(pane.canvasState, pane.previewFigure));
                pane.registerFigure(pane.previewFigure);
                pane.previewFigure = null;
                pane.redrawCanvas();
            }
        };

        abstract void onPressed(PaintPane pane, MouseEvent e);
        abstract void onDragged(PaintPane pane, MouseEvent e);
        abstract void onReleased(PaintPane pane, MouseEvent e);
    }

    private void registerFigure(Figure f) {
        drawerRegistry.put(f, drawerRegistry.get(f.getClass()));
        formatMap.put(f, new FigureFormat())

    }

    private void drawPreviewFigure() {
        if (previewFigure != null) {
            FigureDrawer drawer = drawerResolver.get(previewFigure.getClass());
            if (drawer != null) {
                gc.setStroke(Color.GRAY);
                gc.setLineDashes(5);
                gc.setFill(Color.color(1, 1, 0, 0.4));
                drawer.draw(gc, getFormat(), previewFigure);
                gc.setLineDashes(0);
            }
        }
    }

    private FigureFactory getActiveFactory() {
        ToggleButton button = getSelectedFigureButton();
        return figureFactoryMap.get(button);
    }


    private Mode mode = Mode.DRAW;


    private Point eventStart;
    private Point eventCurrent;


    private Figure hoveredFigure;
    private Figure selectedFigure;
	private Figure previewFigure;



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

		buttonsBox.getChildren().add(borderChoice);


		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);



		canvas.setOnMousePressed(e -> {
            eventStart= eventCurrent;
            mode.onPressed(this, e);
        });
		canvas.setOnMouseReleased(e -> {
            mode.onReleased(this, e);
            eventStart= null;
        });
		canvas.setOnMouseMoved(this::onMouseMoved);
		canvas.setOnMouseDragged(e -> mode.onDragged(this, e));

		deleteButton.setOnAction(event -> {
			if (selectedFigure != null) {
				canvasState.deleteFigure(selectedFigure);
				selectedFigure = null;
				redrawCanvas();
			}
		});

		setLeft(buttonsBox);
		setRight(canvas);


		borderChoice.setOnAction(event ->{
			if(selectedFigure != null){
				figureFormatMap.get(selectedFigure).setBorderStyle(borderChoice.getValue());
			}
		});
	}

	private void onMouseMoved(MouseEvent e) {
		eventCurrent = new Point(e.getX(), e.getY());
		hoveredFigure = canvasState.findTopFigureInPoint(new Point(e.getX(), e.getY()));
		if(hoveredFigure != null){
			statusPane.updateStatus(hoveredFigure.toString());
		} else {
			statusPane.updateStatus(eventCurrent.toString());
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
		if(eventStart == null) {
			return ;
		}
		if(endPoint.getX() < eventStart.getX() || endPoint.getY() < eventStart.getY()) {
			return ;
		}
		ToggleButton button = getSelectedFigureButton();
		if(button == null)
			return;

		//FigureFormat format = new FigureFormat(Color.YELLOW,Color.ORANGE)
		Figure newFigure = figureFactoryMap.get(button).generateFigure(eventStart,endPoint);
		canvasState.executeAction(new AddFigure(canvasState,newFigure));

		figureColorMap.put(newFigure, fillColorPicker.getValue());
		figureFormatMap.put(newFigure, new FigureFormat(fillColorPicker.getValue(), Color.BLACK,borderChoice.getValue()));
		figureDrawerMap.put(newFigure, buttonDrawerMap.get(getSelectedFigureButton()));
		canvasState.addFigure(newFigure);
		eventStart = null;
		redrawCanvas();
	}

	private void onMousePressed(MouseEvent event) {
		Mode.;
	}
}
