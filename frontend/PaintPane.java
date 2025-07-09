package frontend;

import backend.CanvasState;
import backend.actions.effect.AddEffect;
import backend.actions.effect.RemoveEffect;
import backend.actions.figure.AddFigure;
import backend.actions.format.ChangeBorderStyle;
import backend.actions.format.PasteFormat;
import backend.actions.operation.MoveFigure;
import backend.effects.EffectType;
import backend.model.figures.*;
import com.sun.javafx.collections.ObservableMapWrapper;
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

import java.util.EnumSet;
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
     Map<ToggleButton, FigureDrawer> drawerRegistry = new HashMap<>();
    Map<CheckBox, EffectType> effects = new HashMap<>();

	Map<Figure, FigureFormat> formatMap = new HashMap<>();
    Map<Figure, EnumSet<EffectType>> effectMap = new HashMap<>();
    Map<Figure, FigureDrawer> drawerMap = new HashMap<>();


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
                pane.registerFigure(pane.previewFigure, pane.getActiveDrawer());
                pane.previewFigure = null;
                pane.redrawCanvas();
            }
        };

        abstract void onPressed(PaintPane pane, MouseEvent e);
        abstract void onDragged(PaintPane pane, MouseEvent e);
        abstract void onReleased(PaintPane pane, MouseEvent e);
    }

    private void registerFigure(Figure f, FigureDrawer drawer) {
        if (drawer == null) {
            throw new IllegalStateException("No hay drawer para " + f.getClass());
        }
        for (EffectType e : getEffects()) {
            //canvasState.executeAction(new AddEffect(f, e));
        }
        //canvasState.executeAction(new PasteFormat(f,getFormat()));
        formatMap.put(f, getFormat());
        effectMap.put(f, EnumSet.copyOf(getEffects()));
        drawerMap.put(f,drawer); //todo probar

        formatMap.put(f, getFormat());

    }

    private void drawPreviewFigure() {
        if (previewFigure != null) {

            getActiveDrawer().draw(gc, getFormat(), previewFigure, getEffects());

        }
    }

    private FigureFactory getActiveFactory() {
        ToggleButton button = getSelectedFigureButton();
        return factoryMap.get(button);
    }
    private FigureDrawer getActiveDrawer() {
        ToggleButton button = getSelectedFigureButton();
        return drawerRegistry.get(button);
    }
    private FigureFormat getFormat() {
        return new FigureFormat(fillColorPicker.getValue(),Color.BLACK, borderChoice.getValue());
    }
    private EnumSet<EffectType> getEffects() {
        EnumSet<EffectType> active = EnumSet.noneOf(EffectType.class);
        for (Map.Entry<CheckBox, EffectType> entry : effects.entrySet()) {
            if (entry.getKey().isSelected()) {
                active.add(entry.getValue());
            }
        }
        return active;
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


		factoryMap.put(rectangleButton, new RectangleFactory());
        drawerRegistry.put(rectangleButton, new RectangleDrawer());
		factoryMap.put(ellipseButton, new EllipseFactory());
        drawerRegistry.put(ellipseButton,   new EllipseDrawer());
		factoryMap.put(circleButton,new CircleFactory());
        drawerRegistry.put(circleButton,    new CircleDrawer());
		factoryMap.put(squareButton,new SquareFactory());
        drawerRegistry.put(squareButton,    new SquareDrawer());

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
        selectionButton.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                mode = Mode.SELECT;
            } else {
                mode = Mode.DRAW;
            }
        });

		setLeft(buttonsBox);
		setRight(canvas);

        for (EffectType type : EffectType.values()) {
            CheckBox box = new CheckBox(type.getDisplayName());
            effects.put(box, type);
            buttonsBox.getChildren().add(box);

            box.setOnAction(e -> {
                if (selectedFigure == null) {
                    box.setSelected(false);
                    return;
                }
                boolean on = box.isSelected();
                if (on) {
                    //canvasState.executeAction(new AddEffect(selectedFigure, type));
                    effectMap.get(selectedFigure).add(type);
                } else {
                    //canvasState.executeAction(new RemoveEffect(selectedFigure, type));
                    effectMap.get(selectedFigure).remove(type);
                }
            });
        }

        borderChoice.getItems().addAll(BorderStyle.values());
        borderChoice.setValue(BorderStyle.SOLID);

        borderChoice.valueProperty().addListener((obs, old, nw) -> {
            if (selectedFigure != null) {
                // 1) actualiza el formato en memoria
                formatMap.get(selectedFigure).setBorderStyle(nw);
                // 2) registra la acción en el historial
                //canvasState.executeAction(new ChangeBorderStyle(selectedFigure, old, nw));
                redrawCanvas();
            }
        });
		borderChoice.setOnAction(event ->{
			if(selectedFigure != null){
				formatMap.get(selectedFigure).setBorderStyle(borderChoice.getValue());
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
        for (Figure f : canvasState.figures()) {
            FigureFormat fmt    = formatMap.get(f);
            FigureDrawer dr     = drawerMap.get(f);
            EnumSet<EffectType> efs = effectMap.getOrDefault(f, getEffects());

            if (f == selectedFigure) {
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(fmt.getLineColor());
            }
            gc.setFill(fmt.getFillColor());

            dr.draw(gc, fmt, f, efs);
        }
    }


}
