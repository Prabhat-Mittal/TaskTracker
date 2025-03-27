import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonUtilsTest {

  @BeforeClass
  public static void setUpClass() {
  }

  @After
  public void tearDownClass() {
  }

  @Test
  public void testLoadTasksFromFile() throws IOException {
    List<Task> tasks = JsonUtils.loadTasksFromFile(Path.of("src/main/java/tasks.json"));
    Assert.assertFalse(tasks.isEmpty());
  }
}
