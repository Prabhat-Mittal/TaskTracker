import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
  public static List<Task> loadTasksFromFile(Path filePath) throws IOException {
    List<Task> tasks = new ArrayList<>();
    if (Files.exists(filePath)) {
      String jsonContent = Files.readString(filePath);
      String[] taskList = jsonContent.replace("[", "").replace("]", "").split("},");
      for (String taskJson : taskList) {
        if (!taskJson.endsWith("}")) {
          taskJson = taskJson + "}";
        }
        tasks.add(Task.fromJson(taskJson));
      }
    }
    return tasks;
  }

  public static void saveTasksToFile(List<Task> tasks, Path filePath) throws IOException {
    StringBuilder jsonContent = new StringBuilder("[");
    for (int i = 0; i < tasks.size(); i++) {
      jsonContent.append(tasks.get(i).toJson());
      if (i < tasks.size() - 1) {
        jsonContent.append(",");
      }
    }
    jsonContent.append("]");
    Files.writeString(filePath, jsonContent.toString());
  }
}
