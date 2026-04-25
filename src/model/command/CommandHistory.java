package model.command;

import java.util.Stack;

public final class CommandHistory {

    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    public void push(Command command) {
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!canUndo()) return;//undoStack is empty

        Command command = undoStack.pop();
        command.undo();
        redoStack.push(command);
    }

    public void redo() {
        if (!canRedo()) return;
Command command = redoStack.pop();
command.execute();
undoStack.push(command);

    }

    public boolean canUndo() {
        return !undoStack.isEmpty(); //If undoStack has commands → true
        //If empty → false
    }

    public boolean canRedo() {
        return !redoStack.isEmpty();
    }
}