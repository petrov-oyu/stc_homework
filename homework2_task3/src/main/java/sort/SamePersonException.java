package sort;

/**
 * Exception which indicate same persons
 *
 * @author Petrov_OlegYu
 */
public class SamePersonException extends RuntimeException {
	// мёртвый код
	public SamePersonException() {
		super("Persons age and name equals");
	}

	public SamePersonException(String message) {
		super(message);
	}
}
