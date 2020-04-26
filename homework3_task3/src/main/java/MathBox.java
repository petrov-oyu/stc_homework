import java.util.Set;
import java.util.stream.Collectors;
/**
 * homework class, реализующий следующий функционал:
 *
 * Конструктор на вход получает массив Number. Элементы не могут повторяться.
 * Элементы массива внутри объекта раскладываются в подходящую коллекцию (выбрать самостоятельно).
 *
 * Существует метод summator, возвращающий сумму всех элементов коллекции.
 *
 * Существует метод splitter, выполняющий поочередное деление всех хранящихся в объекте элементов на делитель,
 * являющийся аргументом метода. Хранящиеся в объекте данные полностью заменяются результатами деления.
 *
 * Необходимо правильно переопределить методы toString, hashCode, equals,
 * чтобы можно было использовать MathBox для вывода данных на экран и хранение объектов этого класса в коллекциях
 * (например, hashMap). Выполнение контракта обязательно!
 *
 * Создать метод, который получает на вход Integer и если такое значение есть в коллекции, удаляет его.
 *
 * @author Petrov_OlegYu
 */
public class MathBox extends ObjectBox<Number> {
	/**
	 * Конструктор на вход получает массив Number. Элементы не могут повторяться.
	 */
	public MathBox(Set<Number> numbers) {
		this.objects = numbers;
	}

	/**
	 * @return возвращает сумму всех элементов коллекции
	 */
	public double summator() {
		return objects.stream()
				.mapToDouble(Number::doubleValue)
				.sum();
	}

	/**
	 * выполняет поочередное деление всех хранящихся в объекте элементов на делитель,
	 * являющийся аргументом метода.
	 *
	 * Хранящиеся в объекте данные полностью заменяются результатами деления.
	 */
	public void splitter(Number divider) {
		objects = objects.stream()
				.mapToDouble(Number::doubleValue)
				.map(value -> value / divider.doubleValue())
				.boxed()
				.collect(Collectors.toSet());
	}

	/**
	 * получает на вход Integer и если такое значение есть в коллекции, удаляет его.
	 */
	public void removeNumber(Integer removedValue) {
		objects = objects.stream()
				.mapToDouble(Number::doubleValue)
				.filter(currentValue -> !(currentValue == removedValue.doubleValue()))
				.boxed()
				.collect(Collectors.toSet());
	}

//	@Override
//	public void addObject(Number obj) {
//		!!!ClassCastException
//		System.err.println(obj.getClass());
//		super.addObject(obj);
//	}

	@Override
	public String toString() {
		return "MathBox{" +
				"numbers=" + objects +
				'}';
	}
}
