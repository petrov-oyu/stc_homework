import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.io.File;

public class SerializatorTest extends TestCase {
	private File file;
	@Before
	public void setUp() {
		file = new File("d://temp.txt");
	}

	@After
	public void tearDown() {
		file.delete();
	}

	public void testSerialize() {
		ObjectContainer myClass = new ObjectContainer();
		Serializator.serialize(myClass, file);

		assertTrue(file.exists());
	}

	public void testDeserialize() {
		ObjectContainer objectContainer = new ObjectContainer();
		objectContainer.first.myInt = 123;
		objectContainer.first.myLong = 456;
		Serializator.serialize(objectContainer, file);

		ObjectContainer deSerializedClass = (ObjectContainer) Serializator.deSerialize(file);
		MyClass myClass = deSerializedClass.first;
		assertEquals("first", myClass.myString);
		assertEquals(1, myClass.myByte);
		assertEquals(2, myClass.myShort);
		assertEquals(123, myClass.myInt);
		assertEquals(456L, myClass.myLong);
		assertEquals(5.5555555555555F, myClass.myFloat);
		assertEquals(6.6666666666666D, myClass.myDouble);
		assertEquals(true, myClass.myBoolean);

		myClass = deSerializedClass.second;
		assertEquals("second", myClass.myString);
		assertEquals(1, myClass.myByte);
		assertEquals(2, myClass.myShort);
		assertEquals(3, myClass.myInt);
		assertEquals(4L, myClass.myLong);
		assertEquals(5.5555555555555F, myClass.myFloat);
		assertEquals(6.6666666666666D, myClass.myDouble);
		assertEquals(true, myClass.myBoolean);

		myClass = deSerializedClass.third;
		assertEquals("third", myClass.myString);
		assertEquals(1, myClass.myByte);
		assertEquals(2, myClass.myShort);
		assertEquals(3, myClass.myInt);
		assertEquals(4L, myClass.myLong);
		assertEquals(5.5555555555555F, myClass.myFloat);
		assertEquals(6.6666666666666D, myClass.myDouble);
		assertEquals(true, myClass.myBoolean);
	}
}

