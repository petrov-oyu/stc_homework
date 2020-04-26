package sort;

import base.Person;

/**
 * Sorter which use insertion sorting algorithm
 * @author Petrov_OlegYu
 */
public class InsertionSorter implements Sorter {
	@Override
	public void sort(Person[] array) throws SamePersonException {
		long timeNow = System.currentTimeMillis();
		insertionSort(array);
		System.out.println(this.getName() + " spent " + (System.currentTimeMillis() - timeNow) + " ms for sorting");
	}

	public static void insertionSort(Person[] array) {
		for (int i = 1; i < array.length; i++) {
			Person current = array[i];
			int j = i - 1;

			Sorter.isEquals(current, array[j]);
			while (j >= 0 && (current.compareTo(array[j]) > 0)) {
				array[j + 1] = array[j];
				j--;
			}
			// в этой точке мы вышли, так что j так же -1
			// или в первом элементе, где текущий >= a[j]
			array[j + 1] = current;
		}
	}
}
