package model;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description, String status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.status = "NEW";
    }

    public int getEpicId() {
        return epicId;
    }
}
