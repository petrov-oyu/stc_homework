import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Start point for program
 *
 *  @author Petrov_OlegYu
 */
public class Main {
}

/**
 *  Создать класс ObjectBox, который будет хранить коллекцию Object.
 * У класса должен быть метод addObject, добавляющий объект в коллекцию.
 * У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
 * Должен быть метод dump, выводящий содержимое коллекции в строку.
 *
 * @author Petrov_OlegYu
 */
class ObjectBox {
	private Collection<Object> objects = new ArrayList<Object>();

	/**
	 * добавляет объект в коллекцию
	 */
	public void addObject(Object obj) {
		objects.add(obj);
	}

	/**
	 * Проверяет наличие объекта в коллекции и при наличии удаляет его
	 */
	public void deleteObject(Object obj) {
		if (!objects.remove(obj)) {
			System.out.println(obj + " not found");
		};
	}

	/**
	 * Выводит содержимое коллекции в строку
	 */
	public String dump() {
		String objectsString = Arrays.toString(objects.toArray());
		System.out.println(objectsString);
		return objectsString;
	}
}
