package searchingtextinhtml;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Writer {
	public static void writeToFile(String path, List<String> list) {
		Path file = Paths.get(path);
		try {
			Files.write(file, list, StandardCharsets.UTF_8);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
