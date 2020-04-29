import base.Person;
import sort.InsertionSorter;
import sort.QuickSorter;
import sort.SamePersonException;
import sort.Sorter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Start point of program
 *
 * @author Petrov_OlegYu
 */
public class Main {
	public static void main(String[] args) {
		int arraySize = 3;
		Person[] persons = new Person[arraySize];
		// закомментированный код

//		Person[] persons = new Person[arraySize + 4];
		for (int i = 0; i < arraySize; i++) {
			persons[i] = Person.createRandomPerson();
		}

		//should be as builder:
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

		//Если имена людей и возраст совпадают, выбрасывать в программе пользовательское исключение.
		Set<Person> setOfPersons = new HashSet<>();
		setOfPersons.addAll(Arrays.asList(persons));
		if (setOfPersons.size() != persons.length) {
			throw new SamePersonException("You have duplicate persons. Persons age and name should not equals");
		}

		System.out.println("Before sorting: " + Arrays.toString(persons));

		//Программа должна вывести на экран отсортированный список и время работы каждого алгоритма сортировки.
		Person[] insertionPersons = sortPersons(persons, new InsertionSorter());
		Person[] quickPersons = sortPersons(persons, new QuickSorter());


		//tests. it's might be in Test class
		int assertionCount = 10;
		if (assertionCount > arraySize) {
			assertionCount = arraySize;
		}

		// а что если деффект вкрался в оба алгоритма и оба сортируют не правильно?

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
