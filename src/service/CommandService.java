package service;

import model.command.Command;
import model.command.CommandHistory;


public final class CommandService {

    private static volatile CommandService instance;
    private final CommandHistory history;

    private CommandService() {
        this.history = new CommandHistory();
    }

    public static CommandService getInstance() {
        if (instance == null) {
            synchronized (CommandService.class) {
                if (instance == null) {
                    instance = new CommandService();
                }
            }
        }
        return instance;
    }

    public void execute(Command command) {
        if (command == null) return;

        command.execute();
        history.push(command);
    }

    public void undo() {
        history.undo();
    }

    public void redo() {
        history.redo();
    }

    public boolean canUndo() {
        return history.canUndo();
    }

    public boolean canRedo() {
        return history.canRedo();
    }
}