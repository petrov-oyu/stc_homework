/**
 * Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться,
 * но и периодически частично удаляться, чтобы GC имел возможность очищать часть памяти.
 * Через некоторое время программа должна завершиться с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 *
 * Доработать программу так, чтобы ошибка OutOfMemoryError возникала в Metaspace /Permanent Generation
 *
 * @author Petrov_OlegYu
 */
public class HeapMemoryOut {
	private static final int LOOP_COUNT = 1000000000;

	public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
		for (int i = 0; i < LOOP_COUNT; i++) {
			Thread.sleep(1);
			new MyClassLoader().loadClass("MyClass");
		}
	}
}

class MyClass {
}

class MyClassLoader extends ClassLoader {
	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		if (name.equals("MyClass")) {
			byte[] bytes = "class MyClass { };".getBytes();
			return defineClass(name,bytes, 0 ,bytes.length);
		}
		return super.loadClass(name, resolve);
	}
}