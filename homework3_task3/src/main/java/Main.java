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
//		mathBox.addObject("first string");

		mathBox.addObject(1);
		mathBox.addObject(3.5);
		mathBox.addObject(1000.0);
		mathBox.addObject(100.0);

		System.out.println(((MathBox) mathBox).summator());
		mathBox.dump();

		mathBox.deleteObject(100.0);
		System.out.println(((MathBox) mathBox).summator());
		mathBox.dump();

		((MathBox) mathBox).removeNumber(1000);
		System.out.println(((MathBox) mathBox).summator());
		mathBox.dump();

		((MathBox) mathBox).removeNumber(100);
		System.out.println(((MathBox) mathBox).summator());
		mathBox.dump();

		((MathBox) mathBox).removeNumber(3);
		System.out.println(((MathBox) mathBox).summator());
		mathBox.dump();

		assert mathBox.getClass().getSuperclass() == ObjectBox.class;
	}
}
