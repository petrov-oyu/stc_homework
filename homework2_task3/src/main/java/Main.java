import base.Person;
import sort.HeapSorter;
import sort.Sorter;

import java.util.Arrays;

/**
 * Start point of program
 *
 * @author Petrov_OlegYu
 */
public class Main {
	public static void main(String[] args) {
		int arraySize = 10000;
		Person[] persons = new Person[arraySize];
		for (int i = 0; i < arraySize + 1; i++) {
			persons[i] = Person.createRandomPerson();
		}

		System.out.println("Before sorting: " + Arrays.toString(persons));

		Sorter heapSorter = new HeapSorter();
		heapSorter.sort(persons);
		System.out.println("After heap sorting: " + Arrays.toString(persons));

		//TODO check HeapSorter


	}
}
