import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskManager {
  private final Path FILE_PATH = Path.of("src/main/java/tasks.json");
  private final List<Task> tasks;
  Scanner scanner = new Scanner(System.in);

  public TaskManager() {
    this.tasks = loadTasks();
  }

  private static void printHelpMenu() {
    String helper = """
            - add [ Description ],
            - update [id of task] [New Description],
            - delete [id of task],
            - mark-in-progress [id of task],
            - mark-done [id of task],
            - list-all-tasks,
            - list-tasks-that-are-inProgress,
            - list-tasks-that-are-Done,
            - list-tasks-that-are-TODO,
            - list-tasks-that-are-not-Done
            """;
    System.out.println(helper);
  }

  private List<Task> loadTasks() {
    List<Task> storedTasks = new ArrayList<>();

    if (!Files.exists(FILE_PATH)) {
      return new ArrayList<>();
    }

    try {
      String jsonContent = Files.readString(FILE_PATH);
      String[] taskList = jsonContent.replace("[", "").replace("]", "").split("},");

      for (String taskJson : taskList) {
        if (!taskJson.endsWith("}")) {
          taskJson = taskJson + "}";
          storedTasks.add(Task.fromJson(taskJson));
        } else {
          storedTasks.add(Task.fromJson(taskJson));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return storedTasks;
  }

  public void run() {
    printHelpMenu();
    String command;

    while (!(command = scanner.next()).equalsIgnoreCase("exit")) {
      switch (command) {
        case "add" -> addTask();
        case "update" -> updateTask();
        case "delete" -> deleteTask();
        case "mark-in-progress" -> markTaskInProgress();
        case "mark-done" -> markTaskDone();
        case "list-all-tasks" -> listAllTasks();
        case "list-tasks-that-are-inProgress" -> listTasksInProgress();
        case "list-tasks-that-are-Done" -> listTasksDone();
        case "list-tasks-that-are-TODO" -> listTasksTODO();
        case "list-tasks-that-are-not-Done" -> listTasksNotDone();
        case "help" -> printHelpMenu();
        default -> System.out.println("Unknown command. Type 'help' to see the list of commands or 'exit' to exit.");
      }

    }
  }

  private void addTask() {
    String description = scanner.nextLine().trim();
    Task newTask = new Task(description);
    tasks.add(newTask);
    try {
      JsonUtils.saveTasksToFile(tasks, FILE_PATH);
      System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error saving tasks to file: " + e.getMessage());
    }

  }

  public void updateTask() {
    int id = scanner.nextInt();
    String newDescription = scanner.nextLine().trim();
    Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    tasks.stream().filter(task1 -> task1.getId() == id).forEach(task1 -> task1.updateDescription(newDescription));
    try {
      JsonUtils.saveTasksToFile(tasks, FILE_PATH);
      System.out.println("Task updated successfully (ID: " + task.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error saving tasks to file: " + e.getMessage());
    }
  }

  private void deleteTask() {
    int id = scanner.nextInt();
    Task taskForId = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    tasks.removeIf(task -> task.getId() == id);
    try {
      JsonUtils.saveTasksToFile(tasks, FILE_PATH);
      System.out.println("Task deleted successfully if existed (ID: " + taskForId.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error deleting tasks to file: " + e.getMessage());
    }
  }

  private void markTaskInProgress() {
    int id = scanner.nextInt();
    Task taskForId = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    tasks.stream().filter(task1 -> task1.getId() == id).forEach(Task::markInProgress);
    try {
      JsonUtils.saveTasksToFile(tasks, FILE_PATH);
      System.out.println("Task marked as inProgress successfully (ID: " + taskForId.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error while marking task as inProgress: " + e.getMessage());
    }
  }

  private void markTaskDone() {
    int id = scanner.nextInt();
    Task taskForId = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    tasks.stream().filter(task1 -> task1.getId() == id).forEach(Task::markDone);
    try {
      JsonUtils.saveTasksToFile(tasks, FILE_PATH);
      System.out.println("Task marked as done successfully (ID: " + taskForId.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error while marking task as done: " + e.getMessage());
    }
  }

  private void listAllTasks() {
    for (Task task : tasks) {
      System.out.println(task);
    }
  }

  private void listTasksInProgress() {
    for (Task task : tasks) {
      if (task.getStatus() == Status.IN_PROGRESS)
        System.out.println(task);
    }
  }

  private void listTasksDone() {
    for (Task task : tasks) {
      if (task.getStatus() == Status.DONE)
        System.out.println(task);
    }
  }

  private void listTasksTODO() {
    for (Task task : tasks) {
      if (task.getStatus() == Status.TODO)
        System.out.println(task);
    }
  }

  private void listTasksNotDone() {
    for (Task task : tasks) {
      if (!(task.getStatus() == Status.DONE))
        System.out.println(task);
    }
  }

  public Optional<Task> findTask(int id) {
    return tasks.stream().filter(task -> task.getId() == id).findFirst();
  }
}
