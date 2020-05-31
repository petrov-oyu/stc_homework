import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class FactorialCalcTest extends TestCase {
//здесь могли быть тесты)
	public void testCalcFactorial() {
		List<Long> numbers = new ArrayList<>();
		for (int i = 200; i > 0; i--) {
			numbers.add(Long.valueOf(i));
		}
		System.err.println(numbers.toArray().toString());
		FactorialCalc.calcFactorials(numbers);
	}
}