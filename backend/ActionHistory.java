package backend;

import backend.actions.Action;

import java.util.Stack;

public class ActionHistory {
    private final Stack<Action> undoStack = new Stack<>();
    private final Stack<Action> redoStack = new Stack<>();

    public void execute(Action action) {
        action.execute();
        undoStack.push(action);
        redoStack.clear();
    }

    public void undo() {
        if(canUndo()) {
            Action action = undoStack.pop();
            action.undo();
            redoStack.push(action);
        }
    }
    public void redo() {
        if(canRedo()) {
            Action action = redoStack.pop();
            action.execute();
            undoStack.push(action);
        }
    }
    public boolean canUndo(){
        return !undoStack.isEmpty();
    }
    public boolean canRedo(){
        return !redoStack.isEmpty();
    }
}
