package sort;

import base.Person;

/**
 * Sorter which use quick sorting algorithm
 * @author Petrov_OlegYu
 */
public class QuickSorter implements Sorter{
	@Override
	public void sort(Person[] array) {
		long timeNow = System.currentTimeMillis();
		quickSort(array, 0, array.length - 1);
		System.out.println(this.getName() + " spent " + (System.currentTimeMillis() - timeNow) + " ms for sorting");
	}

	private static int partition(Person[] array, int begin, int end) {
		int pivot = end;

		int counter = begin;
		for (int i = begin; i < end; i++) {
			if (array[i].compareTo(array[pivot]) > 0 ) {
				Person temp = array[counter];
				array[counter] = array[i];
				array[i] = temp;
				counter++;
			}
		}
		Person temp = array[pivot];
		array[pivot] = array[counter];
		array[counter] = temp;

		return counter;
	}

	private static void quickSort(Person[] array, int begin, int end) {
		if (end <= begin) return;
		int pivot = partition(array, begin, end);
		quickSort(array, begin, pivot-1);
		quickSort(array, pivot+1, end);
	}
}
