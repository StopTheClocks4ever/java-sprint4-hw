package Model;

import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.status = "NEW";
        subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

}
