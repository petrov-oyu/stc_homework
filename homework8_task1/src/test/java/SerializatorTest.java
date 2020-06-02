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
		MyClass myClass = new MyClass();
		Serializator.serialize(myClass, file);

		assertTrue(file.exists());
	}

	public void testDeserialize() {
		MyClass myClass = new MyClass();
		myClass.myString = "new string";
		myClass.myInt = 999;
		myClass.myLong = 777L;
		Serializator.serialize(myClass, file);

		MyClass deSerializedClass = (MyClass) Serializator.deSerialize(file);
		assertEquals("new string", deSerializedClass.myString);
		assertEquals(1, deSerializedClass.myByte);
		assertEquals(2, deSerializedClass.myShort);
		assertEquals(999, deSerializedClass.myInt);
		assertEquals(777L, deSerializedClass.myLong);
		assertEquals(5.5555555555555F, deSerializedClass.myFloat);
		assertEquals(6.6666666666666D, deSerializedClass.myDouble);
		assertEquals(true, deSerializedClass.myBoolean);
	}

	public void testDeserializeBoxedObject() {
		MyClass myClass = new MyClass();
		myClass.myString = "new string";
		myClass.myInt1 = 999;
		myClass.myLong1 = 777L;
		Serializator.serialize(myClass, file);

		MyClass deSerializedClass = (MyClass) Serializator.deSerialize(file);
		assertEquals("new string", deSerializedClass.myString);
		assertEquals(Byte.valueOf("1"), deSerializedClass.myByte1);
		assertEquals(Short.valueOf("2"), deSerializedClass.myShort1);
		assertEquals(new Integer(999), deSerializedClass.myInt1);
		assertEquals(new Long(777), deSerializedClass.myLong1);
		assertEquals(5.5555555555555F, deSerializedClass.myFloat1);
		assertEquals(6.6666666666666D, deSerializedClass.myDouble1);
		assertEquals(new Boolean(true), deSerializedClass.myBoolean1);
	}
}

