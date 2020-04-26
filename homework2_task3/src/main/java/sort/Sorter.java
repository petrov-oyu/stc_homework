package sort;
import base.Person;

/**
 * Sorter for {@link Person} array
 * @author Petrov_OlegYu
 */
public interface Sorter {

	/**
	 * sort persons depends on it comparable interface.
	 *
	 * @param array of persons
	 * @throws SamePersonException if persons have same age and name
	 */
	void sort(Person[] array) throws SamePersonException;

	/**
	 * Getting name of sorter
	 * @return
	 */
	default String getName() {
		return this.getClass().getSimpleName();
	};

	static void isEquals(Person personFirst, Person personSecond) throws SamePersonException {
		if(personFirst.equals(personSecond)) {
			throw new SamePersonException("Persons age and name equals: "
					+ personFirst
					+ personSecond);
		}
	}
}
