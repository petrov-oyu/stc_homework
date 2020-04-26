import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.DoubleStream;

/**
 * Start point for program
 *
 *  @author Petrov_OlegYu
 */

public class Main {
	public static void main(String[] args) {
	}

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
	class MathBox {
		private DoubleStream doubleStreamOfNumbers;
		/**
		 * Конструктор на вход получает массив Number. Элементы не могут повторяться.
		 */
		public MathBox(Set<Number> numbers) {
			doubleStreamOfNumbers = numbers.stream()
					.mapToDouble(Number::doubleValue);
		}

		/**
		 * @return возвращает сумму всех элементов коллекции
		 */
		public double summator() {
			return doubleStreamOfNumbers.sum();
		}

		/**
		 * выполняет поочередное деление всех хранящихся в объекте элементов на делитель,
		 * являющийся аргументом метода.
		 *
		 * Хранящиеся в объекте данные полностью заменяются результатами деления.
		 */
		public void splitter(Number divider) {
			doubleStreamOfNumbers = doubleStreamOfNumbers
					.map(value -> value / divider.doubleValue());
		}

		/**
		 * получает на вход Integer и если такое значение есть в коллекции, удаляет его.
		 */
		public void removeNumber(Integer removedValue) {
			doubleStreamOfNumbers = doubleStreamOfNumbers
					.filter(currentValue -> !(currentValue == removedValue.doubleValue()));
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof MathBox)) return false;
			MathBox mathBox = (MathBox) o;
			return Objects.equals(doubleStreamOfNumbers, mathBox.doubleStreamOfNumbers);
		}

		@Override
		public int hashCode() {
			return Objects.hash(doubleStreamOfNumbers);
		}

		@Override
		public String toString() {
			return "MathBox{" +
					"numbers=" + Arrays.toString(doubleStreamOfNumbers.toArray()) +
					'}';
		}
	}
}
