package model;

import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.status = "NEW";
        subtaskIds = new ArrayList<>();
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtask(int subtaskId) {
        subtaskIds.remove((Integer) subtaskId);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

}
