import junit.framework.TestCase;

//Здесь ддолжны быть тесты
public class FileCreatorTest extends TestCase {
	public void testGetFiles() {
		new FileCreator().getFiles("d:\\temp\\", 10 , 123091, new String[]{"MYWORD", "MYWORD2"}, 1);
	}
}