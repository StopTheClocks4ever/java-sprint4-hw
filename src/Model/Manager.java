package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int newTaskId = 0;

    private HashMap<Integer, Task> taskDatabase = new HashMap<>();
    private HashMap<Integer, Epic> epicDatabase = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskDatabase = new HashMap<>();

    private int generateNewId() {
        return ++newTaskId;
    }

    //Работа с Task

    public Task createTask(Task task) {
        int id = generateNewId();
        task.setId(id);
        taskDatabase.put(id, task);
        return task;
    }

    public Task getTaskById(int searchedTaskId) {
        return taskDatabase.get(searchedTaskId);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList(taskDatabase.values());
    }

    public void updateTask(Task task) {
        Task checkTask = taskDatabase.get(task.getId());

        if (checkTask == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + task);
            return;
        } else {
            taskDatabase.put(task.getId(), task);
        }
    }

    public void deleteTask(int id) {
        taskDatabase.remove(id);
    }

    public void deleteAllTasks() {
        taskDatabase.clear();
    }

    // Работа с Epic

    public Epic createEpic(Epic epic) {
        int id = generateNewId();
        epic.setId(id);
        epicDatabase.put(id, epic);
        setEpicStatus(epic);
        return epic;
    }

    public void setEpicStatus(Epic epic) {
        ArrayList<String> subtaskStatuses = new ArrayList<>();

        for (Integer id : epic.getSubtaskIds()) {
            Subtask subtask = subtaskDatabase.get(id);
            subtaskStatuses.add(subtask.status);
        }
        if (subtaskStatuses.contains("NEW") && subtaskStatuses.contains("DONE")
                || subtaskStatuses.contains("IN_PROGRESS")) {
            epic.setStatus("IN_PROGRESS");
        } else if (subtaskStatuses.contains("NEW") && !subtaskStatuses.contains("DONE")
                && !subtaskStatuses.contains("IN_PROGRESS") || subtaskStatuses.isEmpty()) {
            epic.setStatus("NEW");
        } else if (!subtaskStatuses.contains("NEW") && subtaskStatuses.contains("DONE")
                && !subtaskStatuses.contains("IN_PROGRESS")) {
            epic.setStatus("DONE");
        }
    }

    public Epic getEpicById(int searchedEpicId) {
        return epicDatabase.get(searchedEpicId);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList(epicDatabase.values());
    }

    public void updateEpic(Epic epic) {
        Epic checkEpic = epicDatabase.get(epic.getId());

        if (checkEpic == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + epic);
            return;
        } else {
            epicDatabase.put(epic.getId(), epic);
        }
    }

    public ArrayList<Subtask> getAllEpicSubtasks(Epic epic) {
        ArrayList<Subtask> currentEpicSubtasks = new ArrayList<>();

        for (int id: epic.subtaskIds) {
            Subtask subtask = subtaskDatabase.get(id);
            currentEpicSubtasks.add(subtask);
        }
        return currentEpicSubtasks;
    }

    public void deleteEpic(int id) {
        epicDatabase.remove(id);
    }

    public void deleteAllEpics() {
        subtaskDatabase.clear();
        epicDatabase.clear();
    }

    // Работа с Subtask

    public Subtask createSubtask(Subtask subtask) {
        int id = generateNewId();
        subtask.setId(id);
        Epic selectedEpic = epicDatabase.get(subtask.getEpicId());
        selectedEpic.subtaskIds.add(subtask.getId());
        subtaskDatabase.put(id, subtask);
        setEpicStatus(selectedEpic);
        return subtask;
    }

    public Subtask getSubtaskById(int searchedSubtaskId) {
        return subtaskDatabase.get(searchedSubtaskId);
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList(subtaskDatabase.values());
    }

    public void updateSubtask(Subtask subtask) {
        Subtask checkSubtask = subtaskDatabase.get(subtask.getId());

        if (checkSubtask == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + subtask);
            return;
        } else {
            subtaskDatabase.put(subtask.getId(), subtask);
            setEpicStatus(epicDatabase.get(subtask.getEpicId()));
        }
    }

    public void deleteSubtask(Integer id) {
        Subtask selectedSubtask = subtaskDatabase.get(id);
        Epic selectedEpic = epicDatabase.get(selectedSubtask.getEpicId());
        subtaskDatabase.remove(id);
        selectedEpic.subtaskIds.remove(id);
        setEpicStatus(selectedEpic);
    }

    public void deleteAllSubtasks() {
        subtaskDatabase.clear();
    }
}
