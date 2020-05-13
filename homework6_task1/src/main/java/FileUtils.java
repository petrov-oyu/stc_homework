import java.io.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Задание 1. Написать программу, читающую текстовый файл.
 * Программа должна составлять отсортированный по алфавиту список слов,
 * найденных в файле и сохранять его в файл-результат.
 * Найденные слова не должны повторяться,
 * регистр не должен учитываться. Одно слово в разных падежах – это разные слова.
 */
public class FileUtils {
	public void sortWordsOnFile() {
		Set<String> words = new HashSet<>(0);

		try (FileReader fr = new FileReader(new File("D:\\test.txt"))) {
			BufferedReader br = new BufferedReader(fr);
			words = br.lines()
					.flatMap((line) -> Stream.of(line.split(" ")))
					.sorted((w1, w2) -> w1.compareToIgnoreCase(w2))
					.collect(Collectors.toCollection(LinkedHashSet::new));
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}

		File resultFile = new File("D:\\test2.txt");
		if (resultFile.exists()) {
			resultFile.delete();
		}

		try (FileWriter fw = new FileWriter(new File("D:\\test2.txt"), true)){
			words.forEach((word) -> {
				try {
					fw.write(word + " ");
				} catch (IOException e) {
					System.err.println(e);
				}
			});
		} catch (IOException e) {
			System.err.println(e);
		}
	};
}
