import base.Person;
import sort.InsertionSorter;
import sort.QuickSorter;
import sort.SamePersonException;
import sort.Sorter;

import java.util.Arrays;

/**
 * Start point of program
 *
 * @author Petrov_OlegYu
 */
public class Main {
	public static void main(String[] args) {
		int arraySize = 3;
		Person[] persons = new Person[arraySize];
//		Person[] persons = new Person[arraySize + 4];
		for (int i = 0; i < arraySize; i++) {
			persons[i] = Person.createRandomPerson();
		}

		//should be builder:
//		Person person = Person.createRandomPerson();
//		person.setSex(person.new Sex(Sex.MAN));
//		person.setAge(10);
//		person.setName("Antony");
//		persons[arraySize] = person;
//
//		person = Person.createRandomPerson();
//		person.setSex(person.new Sex(Sex.MAN));
//		person.setAge(10);
//		person.setName("Alex");
//		persons[arraySize + 1] = person;
//
//		person = Person.createRandomPerson();
//		person.setSex(person.new Sex(Sex.MAN));
//		person.setAge(10);
//		person.setName("Max");
//		persons[arraySize + 2] = person;
//
//		person = Person.createRandomPerson();
//		person.setSex(person.new Sex(Sex.MAN));
//		person.setAge(10);
//		person.setName("Ivan");
//		persons[arraySize + 3] = person;

		System.out.println("Before sorting: " + Arrays.toString(persons));

		Person[] insertionPersons = sortPersons(persons, new InsertionSorter());
		Person[] quickPersons = sortPersons(persons, new QuickSorter());

		int assertionCount = 10;
		if (assertionCount > arraySize) {
			assertionCount = arraySize;
		}

		for (int i = 0; i < assertionCount; i++) {
			assert insertionPersons[i].getAge().equals(quickPersons[i].getAge());
			assert insertionPersons[i].getName().equals(quickPersons[i].getName());
			assert insertionPersons[i].getSex().equals(quickPersons[i].getSex());
		}
	}

	private static Person[] sortPersons(Person[] persons, Sorter sorter) {
		Person[] sortedPersons = Arrays.copyOf(persons, persons.length);
		try {
			sorter.sort(sortedPersons);
		} catch (SamePersonException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("After " + sorter.getName() + ": " + Arrays.toString(sortedPersons));

		return  sortedPersons;
	}

}
