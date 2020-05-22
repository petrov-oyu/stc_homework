import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Дан массив случайных чисел.
 * Написать программу для вычисления
 * факториалов всех элементов массива.
 *
 * использовать пул потоков
 *
 * Особенности выполнения:
 *
 * Для данного примера использовать рекурсию - не очень хороший вариант,
 * т.к. происходит большое выделение памяти, очень вероятен StackOverFlow.
 * Лучше перемножать числа в простом цикле при этом создавать объект типа BigInteger
 *
 * По сути, есть несколько способа решения задания:
 *
 * 1) распараллеливать вычисление факториала для одного числа
 *
 * 2) распараллеливать вычисления для разных чисел
 *
 * 3) комбинированный
 *
 * При чем вычислив факториал для одного числа,
 * можно запомнить эти данные и использовать их для вычисления другого, что будет гораздо быстрее
 */

public class FactorialCalc {
	private static ConcurrentHashMap<Long, BigInteger> factorialResults = new ConcurrentHashMap<>();

	/**
	 * calc and print all factorial results of given numbers
	 * @param numbers
	 */
	static void calcFactorials(List<Long> numbers) {
		AtomicInteger counterOfDoneCalcs  = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(4);

		List<Callable<BigInteger>> taskList = numbers
				.stream()
				.map((n) -> (Callable<BigInteger>) () -> {
					BigInteger factorial = calcFactorial(n);
					counterOfDoneCalcs.getAndIncrement();
					return factorial;
				})
				.collect(Collectors.toList());
		try {
			executor.invokeAll(taskList);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}

		while(counterOfDoneCalcs.get() < numbers.size()) {
			//waiting calculation
		}

		System.err.println(factorialResults.toString());
	}

	/**
	 * Calculate factorial of the number using caching factorial result
	 * @param number number which factorial will be calculated
	 * @return factorial of number
	 */
	static BigInteger calcFactorial(Long number) {
		System.err.println("My number : " + number + ". My thread : " + Thread.currentThread().getName());
		BigInteger result = new BigInteger("1");
		long tempNumber = number;
		while(tempNumber > 1L) {
			BigInteger factorialResult = factorialResults.get(tempNumber);
			if (factorialResult == null) {
				result.multiply(new BigInteger(String.valueOf(tempNumber)));
				tempNumber = tempNumber - 1L;
			} else {
				result.multiply(factorialResult);
				break;
			}
		}

		if (!factorialResults.contains(number)) {
			factorialResults.put(number, result);
		}

		return result;
	}

}
