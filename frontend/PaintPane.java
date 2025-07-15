package frontend;

import backend.CanvasState;
import backend.actions.effect.AddEffect;
import backend.actions.effect.RemoveEffect;
import backend.actions.figure.AddFigure;
import backend.actions.figure.DeleteFigure;
import backend.actions.format.ChangeBorderStyle;
import backend.actions.format.ChangeFillColor;
import backend.actions.format.CopyFormat;
import backend.actions.format.PasteFormat;
import backend.actions.operation.DivideHorizontal;
import backend.actions.operation.DivideVertical;
import backend.actions.operation.MoveFigure;
import backend.actions.operation.MultiplyFigure;
import backend.model.effects.EffectType;
import backend.model.figures.*;
import backend.model.format.ColorData;
import backend.model.format.FigureFormatData;
import frontend.drawers.*;
import frontend.factory.*;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.MoveTo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class PaintPane extends BorderPane {

	// BackEnd
    private  CanvasState canvasState;

	// Canvas y relacionados
    private final Canvas canvas = new Canvas(800, 600);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();


	// Botones Barra Izquierda
	ToggleButton selectionButton = new ToggleButton("Seleccionar");
	Button deleteButton = new Button("Borrar");

	ToggleButton squareButton = new ToggleButton("Cuadrado");
	ToggleButton rectangleButton = new ToggleButton("Rectángulo");
	ToggleButton circleButton = new ToggleButton("Círculo");
	ToggleButton ellipseButton = new ToggleButton("Elipse");

	ChoiceBox<BorderStyle> borderChoice = new ChoiceBox<>();

    private final Color defaultFillColor = Color.YELLOW;
    private final ColorPicker fillColorPicker = new ColorPicker(defaultFillColor);

    private final Button copyFormatButton = new Button("Copiar"); //todo
    private final  Button pasteFormatButton = new Button("Pastar");

    private final    Button undoButton = new Button("Deshacer");
    private final Button redoButton = new Button("Rehacer");
    private final Button divideHorizontalButton = new Button("Dividir Ancho");
    private final  Button divideVerticalButton = new Button("Dividir Alto");
    private final Button multiplyButton = new Button("Multiplicar");
    private final Button moveToButton = new Button("Trasladar");

    private final ChoiceBox<String> layerChoice       = new ChoiceBox<>();
    private final Button      addLayerBtn            = new Button("Agregar Capa");
    private final Button      removeLayerBtn         = new Button("Eliminar Capa");
    private final RadioButton showLayerBtn           = new RadioButton("Mostrar");
    private final RadioButton hideLayerBtn           = new RadioButton("Ocultar");
    private final ToggleGroup layerVisibilityGroup   = new ToggleGroup();
    private final CheckBox    lockLayerBox           = new CheckBox("Bloquear Capa");

	StatusPane statusPane;



	Map<ToggleButton, FigureFactory> factoryMap = new HashMap<>();
    Map<ToggleButton, FigureDrawer> drawerRegistry = new HashMap<>();
    Map<CheckBox, EffectType> effects = new HashMap<>();
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
                    pane.copyFormatButton.setDisable(true);
                }
                else {
                    pane.statusPane.updateStatus(String.format("Se seleccionó: %s", pane.hoveredFigure));
                    pane.copyFormatButton.setDisable(false);
                }
                pane.selectedFigure = pane.hoveredFigure;
                pane.redrawCanvas();

            }
            @Override
            void onDragged(PaintPane pane, MouseEvent e){
                pane.eventCurrent = new Point(e.getX(), e.getY());

                if(pane.selectedFigure == null || pane.eventStart == null) return;
                        double dx =  e.getX() - pane.eventStart.getX();double dy = e.getY() - pane.eventStart.getY();
                        pane.canvasState.executeAction(
                        new MoveFigure(pane.canvasState, pane.selectedFigure, dx, dy)
                );
                pane.redrawCanvas();
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
            pane.eventCurrent = new Point(e.getX(), e.getY());
                FigureFactory factory = pane.getActiveFactory();
                if (factory == null || pane.eventStart==null ) return;
                pane.previewFigure = factory.generateFigure(pane.eventStart, pane.eventCurrent);
                pane.redrawCanvas();
                pane.drawPreviewFigure();
            }
            @Override
            void onReleased(PaintPane pane, MouseEvent e) {
                Figure f = pane.previewFigure;
                FigureDrawer drawer = pane.getActiveDrawer();
                if (f == null) return;
                if (drawer == null) {
                    throw new IllegalStateException("No hay drawer para " + f.getClass());
                }
                pane.canvasState.executeAction(new AddFigure(pane.canvasState, f, pane.getCurrentFormatData(), pane.getEffects()));
                pane.drawerMap.put(f,drawer);
                pane.previewFigure = null;
                pane.redrawCanvas();
            }
        };


        abstract void onPressed(PaintPane pane, MouseEvent e);
        abstract void onDragged(PaintPane pane, MouseEvent e);
        abstract void onReleased(PaintPane pane, MouseEvent e);
    }

    private FigureFormatData getCurrentFormatData() {
        return getFormat().toData();
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
        return new FigureFormat(fillColorPicker.getValue(),borderChoice.getValue());
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

    private void updateUndoRedoButtons() {
        undoButton.setDisable(!canvasState.canUndo());
        redoButton.setDisable(!canvasState.canRedo());
    }


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
        buttonsBox.getChildren().addAll(
                deleteButton,fillColorPicker,borderChoice,copyFormatButton,pasteFormatButton,
                divideHorizontalButton,
                divideVerticalButton,
                multiplyButton,
                moveToButton
        );

        undoButton.setDisable(true);
        redoButton.setDisable(true);
        copyFormatButton.setDisable(true);
        pasteFormatButton.setDisable(true);
        buttonsBox.getChildren().addAll(undoButton, redoButton);

		buttonsBox.setPadding(new Insets(5));
		buttonsBox.setStyle("-fx-background-color: #999");
		buttonsBox.setPrefWidth(100);
		gc.setLineWidth(1);

        layerChoice.getItems().setAll(canvasState.getLayerNames());
        layerChoice.getSelectionModel().selectFirst();

        addLayerBtn.setOnAction(e -> {
            canvasState.addLayer();
            layerChoice.getItems().setAll(canvasState.getLayerNames());
            layerChoice.getSelectionModel().selectLast();
            updateLayerControls();
            redrawCanvas();
        });
        removeLayerBtn.setOnAction(e -> {
            String sel = layerChoice.getValue();
            canvasState.removeLayer(sel);
            layerChoice.getItems().setAll(canvasState.getLayerNames());
            layerChoice.getSelectionModel().selectFirst();
            updateLayerControls();
            redrawCanvas();
        });

        showLayerBtn.setToggleGroup(layerVisibilityGroup);
        hideLayerBtn.setToggleGroup(layerVisibilityGroup);
        showLayerBtn.setOnAction(e -> {
            canvasState.setLayerVisible(layerChoice.getValue(), true);
            redrawCanvas();
        });
        hideLayerBtn.setOnAction(e -> {
            canvasState.setLayerVisible(layerChoice.getValue(), false);
            redrawCanvas();
        });
        lockLayerBox.setOnAction(e -> {
            canvasState.setLayerLocked(layerChoice.getValue(), lockLayerBox.isSelected());
            updateLayerControls(); // para habilitar/deshabilitar botones si está bloqueada
        });
        layerChoice.getSelectionModel().selectedItemProperty().addListener((obs, old, nw) -> {
            canvasState.setCurrentLayer(nw);
            updateLayerControls();
        });
        HBox layersBar = new HBox(10,
                new Label("Capas:"),
                layerChoice,
                addLayerBtn,
                removeLayerBtn,
                showLayerBtn,
                hideLayerBtn,
                lockLayerBox
        );
        layersBar.setPadding(new Insets(5));
        setBottom(layersBar);
        updateLayerControls();

        multiplyButton.setOnAction(ev -> {
            if (selectedFigure == null) return;
            TextInputDialog dlg = new TextInputDialog("3");
            dlg.setHeaderText("Multiplicar figura");
            dlg.setContentText("Ingrese N (>0):");
            dlg.showAndWait().ifPresent(str -> {try {
                int n = Integer.parseInt(str);
                MultiplyFigure action = new MultiplyFigure(canvasState, selectedFigure, n);
                canvasState.executeAction(action);
                FigureDrawer drawer = drawerMap.get(selectedFigure);
                for (Figure f : action.getCreated()) {
                    drawerMap.put(f, drawer);
                }
                selectedFigure = null;
                redrawCanvas();
                updateUndoRedoButtons();
            } catch (NumberFormatException ex) {
                showError("N debe ser un entero >0");
            }
            });
        });
		canvas.setOnMousePressed(e -> {
            eventStart   = new Point(e.getX(), e.getY());
                    eventCurrent = new Point(e.getX(), e.getY());


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
                canvasState.executeAction(new DeleteFigure(canvasState, selectedFigure));
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
		setCenter(canvas);

        fillColorPicker.setOnAction(e -> {
            if (selectedFigure != null) {
                canvasState.executeAction(new ChangeFillColor(canvasState, selectedFigure, getCurrentFormatData().getFillColor()));
                redrawCanvas();
            }
        });

        borderChoice.setOnAction(e -> {
            if (selectedFigure != null) {
                canvasState.executeAction(new ChangeBorderStyle(canvasState, selectedFigure, getCurrentFormatData().getBorderStyle()));
                redrawCanvas();
            }
        });
        copyFormatButton.setOnAction(e -> {
            if (selectedFigure != null) {
            canvasState.executeAction(new CopyFormat(canvasState, selectedFigure));
            pasteFormatButton.setDisable(false);
            updateUndoRedoButtons();
        }});
        pasteFormatButton.setOnAction(e -> {
            if (selectedFigure != null) {
                canvasState.executeAction(new PasteFormat(canvasState, selectedFigure));
                redrawCanvas();
                updateUndoRedoButtons();
            }
        });

        HBox effectsBar = new HBox(10);
        effectsBar.setPadding(new Insets(5));
        for (EffectType type : EffectType.values()) {
            CheckBox box = new CheckBox(type.getDisplayName());
            effects.put(box, type);
            effectsBar.getChildren().add(box);

            box.setOnAction(e -> {
                if (selectedFigure == null) {
                    box.setSelected(false);
                    return;
                }
                boolean on = box.isSelected();
                if (on) {
                    canvasState.executeAction(new AddEffect(canvasState,selectedFigure, type)); //todo
                } else {
                    canvasState.executeAction(new RemoveEffect(canvasState,selectedFigure, type));
                }
                redrawCanvas();
            });
        }
        effectsBar.setStyle("-fx-background-color: #eee");
        setTop(effectsBar);

        undoButton.setOnAction(e -> {
            canvasState.undo();
            redrawCanvas();
            updateUndoRedoButtons();
        });
        redoButton.setOnAction(e -> {
            canvasState.redo();
            redrawCanvas();
            updateUndoRedoButtons();
        });
        divideHorizontalButton.setOnAction(e -> {
            if (selectedFigure == null) return;
            TextInputDialog dialog = new TextInputDialog("2");
            dialog.setHeaderText("Dividir a lo ancho");
            dialog.setContentText("Ingrese el valor de N:");
            dialog.showAndWait().ifPresent(input -> {
                try {
                    int n = Integer.parseInt(input);
                    if (n <= 0) throw new NumberFormatException();
                    FigureDrawer drawer = drawerMap.get(selectedFigure);
                    DivideHorizontal action = new DivideHorizontal(canvasState, selectedFigure, n);
                    canvasState.executeAction(action);
                    selectedFigure = null;
                    for (Figure f : action.getCreated()) {
                        drawerMap.put(f, drawer);
                    }
                    redrawCanvas();
                } catch (NumberFormatException ex) {
                    showError("Ingrese un número entero positivo válido.");
                }
            });
            updateUndoRedoButtons();

        });
        divideVerticalButton.setOnAction(ev -> {
            if (selectedFigure == null) return;
            TextInputDialog dlg = new TextInputDialog("2");
            dlg.setHeaderText("Dividir a lo alto");
            dlg.setContentText("Ingrese N (>0):");
            dlg.showAndWait().ifPresent(str -> {
                try {
                    int n = Integer.parseInt(str);
                    DivideVertical action = new DivideVertical(canvasState, selectedFigure, n);
                    FigureDrawer drawer = drawerMap.get(selectedFigure);
                    canvasState.executeAction(action);
                    for (Figure f : action.getCreated()) {
                        drawerMap.put(f, drawer);
                    }
                    selectedFigure = null;
                    redrawCanvas();
                    updateUndoRedoButtons();
                } catch (NumberFormatException ex) {
                    showError("N debe ser un entero >0");
                }
            });                updateUndoRedoButtons();

        });

        moveToButton.setOnAction(e -> {
            if (selectedFigure == null) return;
            TextInputDialog dialog = new TextInputDialog("100,200");
            dialog.setHeaderText("Trasladar figura");
            dialog.setContentText("Ingrese coordenadas X,Y:");
            dialog.showAndWait().ifPresent(input -> {
                try {
                    String[] parts = input.split(",");
                    if (parts.length != 2) throw new IllegalArgumentException();
                    double x = Double.parseDouble(parts[0].trim());
                    double y = Double.parseDouble(parts[1].trim());
                    canvasState.executeAction(new MoveFigure(canvasState, selectedFigure, x, y));//todo
                    redrawCanvas();
                } catch (Exception ex) {
                    showError("Ingrese las coordenadas en formato válido: X,Y");
                }
            });                updateUndoRedoButtons();

        });
        borderChoice.getItems().addAll(BorderStyle.values());
        borderChoice.setValue(BorderStyle.SOLID);



	}

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void updateLayerControls() {
        String name = layerChoice.getValue();
        // obtengo la capa actual del modelo para conocer sus flags
        // (asumimos que CanvasState expone métodos `isLayerVisible` e `isLayerLocked`)
        boolean visible = canvasState.isLayerVisible(name);
        boolean locked  = canvasState.isLayerLocked(name);
        // Sincronizo los RadioButton y CheckBox
        showLayerBtn.setSelected(visible);
        hideLayerBtn.setSelected(!visible);
        lockLayerBox.setSelected(locked);

        removeLayerBtn.setDisable(locked);

        // Si la capa está bloqueada, deshabilito botones de acción sobre ella
        divideHorizontalButton.setDisable(locked);
        divideVerticalButton.setDisable(locked);
        multiplyButton.setDisable(locked);
        moveToButton.setDisable(locked);
        copyFormatButton.setDisable(locked);
        pasteFormatButton.setDisable(locked);
    }


    private void onMouseMoved(MouseEvent e) {
		Point mousePos = new Point(e.getX(), e.getY());
		hoveredFigure = canvasState.findTopFigureInPoint(new Point(e.getX(), e.getY()));
        if(selectedFigure != null) return;
		if(hoveredFigure != null){
			statusPane.updateStatus(hoveredFigure.toString());
		} else {
			statusPane.updateStatus(mousePos.toString());
		}
	}

    void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Figure f : canvasState) {
            FigureFormatData data = f.getFormat();
            FigureFormat fmt      = new FigureFormat(data);
            EnumSet<EffectType> efs = EnumSet.copyOf(f.getEffects());
            FigureDrawer dr     = drawerMap.get(f);

            if (f == selectedFigure) {
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(Color.BLACK);//fmt.getLineColor());
            }
            gc.setFill(fmt.getFillColor());

            dr.draw(gc, fmt, f, efs);
        }
        updateUndoRedoButtons();
    }


}
