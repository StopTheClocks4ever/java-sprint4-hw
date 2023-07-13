package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int newTaskId = 0;

    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    private int generateNewId() {
        return ++newTaskId;
    }

    //Работа с Task

    public Task createTask(Task task) {
        int id = generateNewId();
        task.setId(id);
        taskMap.put(id, task);
        return task;
    }

    public Task getTaskById(int searchedTaskId) {
        return taskMap.get(searchedTaskId);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList(taskMap.values());
    }

    public void updateTask(Task task) {
        Task checkTask = taskMap.get(task.getId());

        if (checkTask == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + task);
            return;
        } else {
            taskMap.put(task.getId(), task);
        }
    }

    public void deleteTask(int id) {
        taskMap.remove(id);
    }

    public void deleteAllTasks() {
        taskMap.clear();
    }

    // Работа с Epic

    public Epic createEpic(Epic epic) {
        int id = generateNewId();
        epic.setId(id);
        epicMap.put(id, epic);
        setEpicStatus(epic);
        return epic;
    }

    private void setEpicStatus(Epic epic) {
        ArrayList<String> subtaskStatuses = new ArrayList<>();

        for (Integer id : epic.getSubtaskIds()) {
            Subtask subtask = subtaskMap.get(id);
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
        return epicMap.get(searchedEpicId);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList(epicMap.values());
    }

    public void updateEpic(Epic epic) {
        Epic checkEpic = epicMap.get(epic.getId());

        if (checkEpic == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + epic);
            return;
        } else {
            checkEpic.setName(epic.getName());
            checkEpic.setDescription(epic.getDescription());
        }
    }

    public ArrayList<Subtask> getAllEpicSubtasks(Epic epic) {
        ArrayList<Subtask> currentEpicSubtasks = new ArrayList<>();

        for (int id: epic.subtaskIds) {
            Subtask subtask = subtaskMap.get(id);
            currentEpicSubtasks.add(subtask);
        }
        return currentEpicSubtasks;
    }

    public void deleteEpic(int id) {
        ArrayList<Integer> subtaskIdsCopy = new ArrayList<>(getEpicById(id).getSubtaskIds());
        for (int i = 0; i < subtaskIdsCopy.size(); i++) {
            int subtaskId = subtaskIdsCopy.get(i);
            deleteSubtask(subtaskId);
        }
        epicMap.remove(id);
    }

    public void deleteAllEpics() {
        subtaskMap.clear();
        epicMap.clear();
    }

    // Работа с Subtask

    public Subtask createSubtask(Subtask subtask) {
        int id = generateNewId();
        subtask.setId(id);
        Epic selectedEpic = epicMap.get(subtask.getEpicId());
        if (selectedEpic == null) {
            System.out.println("Эпика для указанной подзадачи не существует");
        } else {
            selectedEpic.addSubtask(subtask.getId());
            subtaskMap.put(id, subtask);
            setEpicStatus(selectedEpic);
        } return subtask;
    }

    public Subtask getSubtaskById(int searchedSubtaskId) {
        return subtaskMap.get(searchedSubtaskId);
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList(subtaskMap.values());
    }

    public void updateSubtask(Subtask subtask) {
        Subtask checkSubtask = subtaskMap.get(subtask.getId());

        if (checkSubtask == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + subtask);
            return;
        } else {
            subtaskMap.put(subtask.getId(), subtask);
            setEpicStatus(epicMap.get(subtask.getEpicId()));
        }
    }

    public void deleteSubtask(Integer id) {
        Subtask selectedSubtask = subtaskMap.get(id);
        Epic selectedEpic = epicMap.get(selectedSubtask.getEpicId());
        subtaskMap.remove(id);
        selectedEpic.removeSubtask(id);
        setEpicStatus(selectedEpic);
    }

    public void deleteAllSubtasks() {
        subtaskMap.clear();
        for (Epic epic: epicMap.values()) {
            setEpicStatus(epic);
        }
    }
}
