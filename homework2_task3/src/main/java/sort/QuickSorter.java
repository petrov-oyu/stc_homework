package sort;

import base.Person;

/**
 * Sorter which use quick sorting algorithm
 * @author Petrov_OlegYu
 */
public class QuickSorter implements Sorter{
	@Override
	public void sort(Person[] array) {
		//TODO implements
		long timeNow = System.currentTimeMillis();
		System.out.println("QuickSorter spent " + (System.currentTimeMillis() - timeNow) + " ms for sorting");
	}
}
