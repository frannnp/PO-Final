package backend.actions;

public interface Action {
    void execute();
    void undo();
}
