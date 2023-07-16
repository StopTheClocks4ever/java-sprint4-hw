package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_HISTORY_SIZE = 10;
    private final List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        return history;
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
            history.remove(0);
        }
    }
}
