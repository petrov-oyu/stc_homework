package sort;

import base.Person;

/**
 * Sorter which use heap sorting algorithm
 * @author Petrov_OlegYu
 */
public class HeapSorter implements Sorter {
	@Override
	public void sort(Person[] array) throws SamePersonException {
		long timeNow = System.currentTimeMillis();
		heapSort(array);
		System.out.println("HeapSorter spent " + (System.currentTimeMillis() - timeNow) + " ms for sorting");
	}

	private static void isEquals(Person personFirst, Person personSecond) throws SamePersonException {
		if(personFirst.equals(personSecond)) {
			throw new SamePersonException("Persons age and name equals: "
					+ personFirst
					+ personSecond);
		}
	}

	private static void heapify(Person[] array, int length, int i) throws SamePersonException {
		int leftChild = 2*i+1;
		int rightChild = 2*i+2;
		int largest = i;

		// если левый дочерний больше родительского
		isEquals(array[leftChild], array[largest]);
		if (leftChild < length && array[leftChild].compareTo(array[largest]) == 1) {
			largest = leftChild;
		}

		// если правый дочерний больше родительского
		isEquals(array[rightChild], array[largest]);
		if (rightChild < length && array[rightChild].compareTo(array[largest]) == 1) {
			largest = rightChild;
		}

		// если должна произойти замена
		if (largest != i) {
			Person temp = array[i];
			array[i] = array[largest];
			array[largest] = temp;
			heapify(array, length, largest);
		}
	}

	private static void heapSort(Person[] array) throws SamePersonException {
		if (array.length == 0) return;

		// Строим кучу
		int length = array.length;
		// проходим от первого без ответвлений к корню
		for (int i = length / 2-1; i >= 0; i--)
			heapify(array, length, i);

		for (int i = length-1; i >= 0; i--) {
			Person temp = array[0];
			array[0] = array[i];
			array[i] = temp;

			heapify(array, i, 0);
		}
	}
}
