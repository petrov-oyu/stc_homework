import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

//Здесь ддолжны быть тесты
public class FileCreatorTest extends TestCase {
	public void testGetFiles() {
		List<String> userWords = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			userWords.add("MYWORD_" + i);
		}

		new FileCreator().getFiles("d:\\temp\\", 10 , 123091, userWords, 1);
	}
}