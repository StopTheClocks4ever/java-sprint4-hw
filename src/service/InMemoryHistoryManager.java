package service;

import model.Task;

import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public LinkedList<Task> getHistory() {
        return new LinkedList<>(history);
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        history.add(task);
        clearExcessHistory();
    }

    private void clearExcessHistory() {
        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
    }
}
