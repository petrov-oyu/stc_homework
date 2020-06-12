import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Необходимо создать программу, которая продемонстрирует утечку памяти в Java.
 * При этом объекты должны не только создаваться,
 * но и периодически частично удаляться, чтобы GC имел возможность очищать часть памяти.
 * Через некоторое время программа должна завершиться с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 *
 * @author Petrov_OlegYu
 */
public class HeapMemoryOut {
	private static final int LOOP_BYTES = 100000;
	private static final int LOOP_COUNT = 1000000;

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(2000);

		List<int[]> list = new ArrayList<int[]>();

		Random random = new Random();
		for (int i = 0; i < LOOP_COUNT; i++) {
			System.out.println("Free Memory: " + Runtime.getRuntime().freeMemory());
			int[] myInts = new int[LOOP_BYTES];
			list.add(myInts);
			if (i % 10 == 0) {
				Thread.sleep(1);
				list.remove(0);
			}
		}
		System.out.println(list.size());
	}
}