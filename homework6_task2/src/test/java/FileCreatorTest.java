import junit.framework.TestCase;

public class FileCreatorTest extends TestCase {

	public void testGetFiles() {
		new FileCreator().getFiles("c:\\temp.txt", 1 , 100, new String[]{"", ""}, 1);
	}
}