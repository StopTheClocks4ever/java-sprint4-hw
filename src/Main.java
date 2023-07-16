import model.Epic;
import model.TaskStatus;
import service.InMemoryTaskManager;
import model.Subtask;
import model.Task;
import service.Managers;
import service.TaskManager;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        TaskManager inMemoryTaskManager = Managers.getDefault();

        Task task1 = new Task("Задача №1", "Описание задачи №1");
        Task task2 = new Task("Задача №2", "Описание задачи №2");

        task1 = inMemoryTaskManager.createTask(task1);
        task2 = inMemoryTaskManager.createTask(task2);

        Epic epic3 = new Epic("Эпик с индексом №3", "Описание эпика");

        epic3 = inMemoryTaskManager.createEpic(epic3);

        Subtask subtask4ForEpic3 = new Subtask("Подзадача (индекс №4) для эпика №3",
                "Описание подзадачи", 3);
        Subtask subtask5ForEpic3 = new Subtask("Подзадача (индекс №5) для эпика №3",
                "Описание подзадачи", 3);

        subtask4ForEpic3 = inMemoryTaskManager.createSubtask(subtask4ForEpic3);
        subtask5ForEpic3 = inMemoryTaskManager.createSubtask(subtask5ForEpic3);

        Epic epic6 = new Epic("Эпик с индексом №6", "Описание эпика");

        epic6 = inMemoryTaskManager.createEpic(epic6);

        Subtask subtask7ForEpic6 = new Subtask("Подзадача (индекс №7) для эпика №6",
                "Описание подзадачи", 6);

        subtask7ForEpic6 = inMemoryTaskManager.createSubtask(subtask7ForEpic6);

        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic3);
        System.out.println(subtask4ForEpic3);
        System.out.println(subtask5ForEpic3);
        System.out.println(epic6);
        System.out.println(subtask7ForEpic6 + "\n");

        subtask4ForEpic3.setStatus(TaskStatus.DONE);
        inMemoryTaskManager.updateSubtask(subtask4ForEpic3);

        System.out.println(epic3);
        System.out.println(subtask4ForEpic3);
        System.out.println(subtask5ForEpic3 + "\n");

        subtask7ForEpic6.setStatus(TaskStatus.IN_PROGRESS);
        inMemoryTaskManager.updateSubtask(subtask7ForEpic6);

        System.out.println(epic6);
        System.out.println(subtask7ForEpic6 + "\n");

        inMemoryTaskManager.deleteSubtask(5);

        System.out.println(epic3 + "\n");

        inMemoryTaskManager.deleteEpic(3);

        System.out.println(inMemoryTaskManager.getAllEpics() + "\n");

        // Проверяю работу истории задач

        inMemoryTaskManager.getEpicById(6);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(6);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getEpicById(6);
        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(2);
        inMemoryTaskManager.getTaskById(2);

        List<Task> history = inMemoryTaskManager.getHistory();

        System.out.println("История просмотров задач: ");

        for (Task element : history) {
            System.out.println(element);
        }
        // Добавляю десятый просмотр

        inMemoryTaskManager.getEpicById(6);

        List<Task> historyFull = inMemoryTaskManager.getHistory();

        System.out.println("История просмотров задач: ");

        for (Task element : historyFull) {
            System.out.println(element);
        }
    }
}