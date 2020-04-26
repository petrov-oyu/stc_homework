import java.util.HashSet;

/**
 * Start point for program
 *
 * Доработать классы MathBox и ObjectBox таким образом, чтобы MathBox был наследником ObjectBox.
 * Необходимо сделать такую связь, правильно распределить поля и методы.
 * Функциональность в целом должна сохраниться.
 * При попытке положить Object в MathBox должно создаваться исключение.
 *
 *  @author Petrov_OlegYu
 */

public class Main {
	public static void main(String[] args) {
		ObjectBox mathBox = new MathBox(new HashSet<Number>());
		mathBox.addObject("first string");
		mathBox.addObject("second string");
		mathBox.addObject("third string");
		mathBox.addObject("fourth string");
		mathBox.addObject("fifth string");

		mathBox.deleteObject("second string");

		mathBox.dump();

		assert mathBox.getClass().getSuperclass() == ObjectBox.class;
	}
}
