import java.io.*;

/**
 * Задание 1. Необходимо разработать класс, реализующий следующие методы:
 *
 * void serialize (Object object, String file);
 *
 * Object deSerialize(String file);
 *
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 * Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 *
 * @author Petrov_OlegYu
 */
public class Serializator {
	/**
	 * Выполняет сериализацию объекта Object в файл file.
	 * Delete file content and serialize object to file
	 * @param object
	 * @param file
	 */
	static void serialize (Object object, File file) {
		try (FileOutputStream fos = new FileOutputStream(file);
		     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(object);
		} catch (IOException e) {
			System.err.println("Cannot write file : " + e);
		}
	}

	/**
	 * Выполняет десериализацию объекта из файла.
	 *
	 * Deserialize object from file
	 * @param file
	 * @return
	 */
	static Object deSerialize(File file) {
		try (FileInputStream fis = new FileInputStream(file);
		    ObjectInputStream ois = new ObjectInputStream(fis)) {
			return ois.readObject();
		} catch (IOException e) {
			System.err.println("Cannot read to file : " + e);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found : " + e);
		}
		return null;
	}
}
