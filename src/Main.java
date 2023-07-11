import model.Epic;
import model.Manager;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        Task task1 = new Task("Задача №1", "Описание задачи №1");
        Task task2 = new Task("Задача №2", "Описание задачи №2");

        task1 = manager.createTask(task1);
        task2 = manager.createTask(task2);

        Epic epic3 = new Epic("Эпик с индексом №3", "Описание эпика");

        epic3 = manager.createEpic(epic3);

        Subtask subtask4ForEpic3 = new Subtask("Подзадача (индекс №4) для эпика №3",
                "Описание подзадачи", 3);
        Subtask subtask5ForEpic3 = new Subtask("Подзадача (индекс №5) для эпика №3",
                "Описание подзадачи", 3);

        subtask4ForEpic3 = manager.createSubtask(subtask4ForEpic3);
        subtask5ForEpic3 = manager.createSubtask(subtask5ForEpic3);

        Epic epic6 = new Epic("Эпик с индексом №6", "Описание эпика");

        epic6 = manager.createEpic(epic6);

        Subtask subtask7ForEpic6 = new Subtask("Подзадача (индекс №7) для эпика №6",
                "Описание подзадачи", 6);

        subtask7ForEpic6 = manager.createSubtask(subtask7ForEpic6);

        System.out.println(task1);
        System.out.println(task2);
        System.out.println(epic3);
        System.out.println(subtask4ForEpic3);
        System.out.println(subtask5ForEpic3);
        System.out.println(epic6);
        System.out.println(subtask7ForEpic6 + "\n");

        subtask4ForEpic3.setStatus("DONE");
        manager.updateSubtask(subtask4ForEpic3);

        System.out.println(epic3);
        System.out.println(subtask4ForEpic3);
        System.out.println(subtask5ForEpic3 + "\n");

        subtask7ForEpic6.setStatus("IN_PROGRESS");
        manager.updateSubtask(subtask7ForEpic6);

        System.out.println(epic6);
        System.out.println(subtask7ForEpic6 + "\n");

        manager.deleteSubtask(5);

        System.out.println(epic3 + "\n");

        manager.deleteEpic(3);

        System.out.println(manager.getAllEpics());
    }
}