package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int newTaskId = 0;

    private HashMap<Integer, Task> taskMap = new HashMap<>();
    private HashMap<Integer, Epic> epicMap = new HashMap<>();
    private HashMap<Integer, Subtask> subtaskMap = new HashMap<>();

    private HistoryManager historyManager = Managers.getDefaultHistory();

    private int generateNewId() {
        return ++newTaskId;
    }

    //Работа с Task
    @Override
    public Task createTask(Task task) {
        int id = generateNewId();
        task.setId(id);
        taskMap.put(id, task);
        return task;
    }
    @Override
    public Task getTaskById(int searchedTaskId) {
        Task task = taskMap.get(searchedTaskId);
        historyManager.addTask(task);
        return task;
    }
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList(taskMap.values());
    }
    @Override
    public void updateTask(Task task) {
        Task checkTask = taskMap.get(task.getId());

        if (checkTask == null) {
            System.out.println("Обновление статуса несуществующей задачи: " + task);
            return;
        } else {
            taskMap.put(task.getId(), task);
        }
    }
    @Override
    public void deleteTask(int id) {
        taskMap.remove(id);
    }
    @Override
    public void deleteAllTasks() {
        taskMap.clear();
    }

    // Работа с Epic
    @Override
    public Epic createEpic(Epic epic) {
        int id = generateNewId();
        epic.setId(id);
        epicMap.put(id, epic);
        setEpicStatus(epic);
        return epic;
    }

    private void setEpicStatus(Epic epic) {
        ArrayList<TaskStatus> subtaskStatuses = new ArrayList<>();

        for (Integer id : epic.getSubtaskIds()) {
            Subtask subtask = subtaskMap.get(id);
            subtaskStatuses.add(subtask.status);
        }
        if (subtaskStatuses.contains(TaskStatus.NEW) && subtaskStatuses.contains(TaskStatus.DONE)
                || subtaskStatuses.contains(TaskStatus.IN_PROGRESS)) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else if (subtaskStatuses.contains(TaskStatus.NEW) && !subtaskStatuses.contains(TaskStatus.DONE)
                && !subtaskStatuses.contains(TaskStatus.IN_PROGRESS) || subtaskStatuses.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else if (!subtaskStatuses.contains(TaskStatus.NEW) && subtaskStatuses.contains(TaskStatus.DONE)
                && !subtaskStatuses.contains(TaskStatus.IN_PROGRESS)) {
            epic.setStatus(TaskStatus.DONE);
        }
    }
    @Override
    public Epic getEpicById(int searchedEpicId) {
        Epic epic = epicMap.get(searchedEpicId);
        historyManager.addTask(epic);
        return epic;
    }
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList(epicMap.values());
    }
    @Override
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
    @Override
    public ArrayList<Subtask> getAllEpicSubtasks(Epic epic) {
        ArrayList<Subtask> currentEpicSubtasks = new ArrayList<>();

        for (int id: epic.getSubtaskIds()) {
            Subtask subtask = subtaskMap.get(id);
            currentEpicSubtasks.add(subtask);
        }
        return currentEpicSubtasks;
    }
    @Override
    public void deleteEpic(int id) {
        Epic epic = epicMap.get(id);
        ArrayList<Integer> subtaskIdsCopy = new ArrayList<>(epic.getSubtaskIds());
        for (int i = 0; i < subtaskIdsCopy.size(); i++) {
            int subtaskId = subtaskIdsCopy.get(i);
            deleteSubtask(subtaskId);
        }
        epicMap.remove(id);
    }
    @Override
    public void deleteAllEpics() {
        subtaskMap.clear();
        epicMap.clear();
    }

    // Работа с Subtask
    @Override
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
    @Override
    public Subtask getSubtaskById(int searchedSubtaskId) {
        Subtask subtask = subtaskMap.get(searchedSubtaskId);
        historyManager.addTask(subtask);
        return subtask;
    }
    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList(subtaskMap.values());
    }
    @Override
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
    @Override
    public void deleteSubtask(Integer id) {
        Subtask selectedSubtask = subtaskMap.get(id);
        Epic selectedEpic = epicMap.get(selectedSubtask.getEpicId());
        subtaskMap.remove(id);
        selectedEpic.removeSubtask(id);
        setEpicStatus(selectedEpic);
    }
    @Override
    public void deleteAllSubtasks() {
        subtaskMap.clear();
        for (Epic epic: epicMap.values()) {
            setEpicStatus(epic);
        }
    }
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
