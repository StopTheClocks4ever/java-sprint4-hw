package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    Task createTask(Task task);

    Task getTaskById(int searchedTaskId);

    ArrayList<Task> getAllTasks();

    void updateTask(Task task);

    void deleteTask(int id);

    void deleteAllTasks();

    Epic createEpic(Epic epic);

    Epic getEpicById(int searchedEpicId);

    ArrayList<Epic> getAllEpics();

    void updateEpic(Epic epic);

    ArrayList<Subtask> getAllEpicSubtasks(Epic epic);

    void deleteEpic(int id);

    void deleteAllEpics();

    Subtask createSubtask(Subtask subtask);

    Subtask getSubtaskById(int searchedSubtaskId);

    ArrayList<Subtask> getAllSubtasks();

    void updateSubtask(Subtask subtask);

    void deleteSubtask(Integer id);

    void deleteAllSubtasks();

    List<Task> getHistory();

}
