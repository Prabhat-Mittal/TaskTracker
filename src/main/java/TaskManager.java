import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TaskManager {
  Scanner scanner = new Scanner(System.in);
  private final Path FILE_PATH = Path.of("src/main/java/tasks.json");
  private List<Task> tasks;
  private static final ObjectMapper mapper;
  static {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
    mapper.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
  }

  public TaskManager() {
    this.tasks = loadTasks();
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

    while (!(command = scanner.next().toLowerCase()).equals("exit")) {
      switch (command) {
        case "add" -> addTask();
        case "update" -> updateTask();
        case "help" -> printHelpMenu();
        default -> System.out.println("Unknown command. Type 'help' to see the list of commands or 'exit' to exit.");
      }

    }
  }

  private static void printHelpMenu() {
    String helper = """
            - add [ Description ],
            - update [id of task] [New Description]
            """;
    System.out.println(helper);
  }

  private void addTask() {
    String description = scanner.nextLine().trim();
    Task newTask = new Task(description);
    tasks.add(newTask);
    try {
      mapper.writeValue(new File(FILE_PATH.toString()), tasks);
      System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error saving tasks to file: " + e.getMessage());
    }

  }

  public void updateTask() {
    int id = scanner.nextInt();
    String newDescription = scanner.nextLine().trim();
    Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found!"));
    tasks.stream().filter(task1->task1.getId()==id).forEach(task1 -> task1.updateDescription(newDescription));
    try {
      mapper.writeValue(new File(FILE_PATH.toString()), tasks);
      System.out.println("Task updated successfully (ID: " + task.getId() + ")");
    } catch (IOException e) {
      System.out.println("Error saving tasks to file: " + e.getMessage());
    }
  }

  public Optional<Task> findTask(int id) {
    return tasks.stream().filter(task -> task.getId() == id).findFirst();
  }
}
