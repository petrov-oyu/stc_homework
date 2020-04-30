import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 *  Создать класс ObjectBox, который будет хранить коллекцию Object.
 * У класса должен быть метод addObject, добавляющий объект в коллекцию.
 * У класса должен быть метод deleteObject, проверяющий наличие объекта в коллекции и при наличии удаляющий его.
 * Должен быть метод dump, выводящий содержимое коллекции в строку.
 *
 * @author Petrov_OlegYu
 */
class ObjectBox<T>{
	protected Collection<T> objects = new ArrayList<T>();

	/**
	 * добавляет объект в коллекцию
	 */
	public void addObject(T obj) {
		objects.add(obj);
	}

	/**
	 * Проверяет наличие объекта в коллекции и при наличии удаляет его
	 */
	public void deleteObject(T obj) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ObjectBox)) return false;
		ObjectBox<?> objectBox = (ObjectBox<?>) o;
		return Objects.equals(objects, objectBox.objects);
	}

	@Override
	public int hashCode() {
		return Objects.hash(objects);
	}
}
