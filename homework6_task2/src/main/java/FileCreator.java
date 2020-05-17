import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * Задание 2. Создать генератор текстовых файлов, работающий по следующим правилам:
 *
 * Предложение состоит из 1<=n1<=15 слов. В предложении после произвольных слов могут находиться запятые.
 * Слово состоит из 1<=n2<=15 латинских букв
 * Слова разделены одним пробелом
 * Предложение начинается с заглавной буквы
 * Предложение заканчивается (.|!|?)+" "
 * Текст состоит из абзацев. в одном абзаце 1<=n3<=20 предложений. В конце абзаца стоит разрыв строки и перенос каретки.
 * Есть массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива
 *      в следующее предложение (1/probability).
 * Необходимо написать метод getFiles(String path, int n, int size, String[] words, int probability),
 *      который создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
 *
 * Represents a util for creation text file with rules:
 * 1) random from 1 to 15 words
 * 2) word has from 1 to 15 chars
 * 3) words can be separate by " " or ", "
 * 4)
 * etc. see top
 *
 *
 * @author Petrov_OlegYu
 */
public class FileCreator {
	private static final Charset CHARSET = Charset.forName("UTF-8");
	private static final Random RANDOM = new Random();
	private static final int CHARS_IN_WORD = 15;
	private static final int WORDS_IN_SENTENCE = 15;
	private static final int SENTENCES_IN_PARAGRAPH = 20;
	private static final String[] WORD_SEPARATORS = new String[]{" ", ", "};
	private static final String[] SENTENCE_SEPARATORS = new String[]{". ", "! ", "? "};
	private static final String[] PARAGRAPH_SEPARATORS = new String[]{System.lineSeparator() + System.lineSeparator()};
	private static final String[] VALID_CHARS = new String[]{"q", "w", "e", "r", "t", "y",
			"u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};


	//TODO это больше похоже на несколько билдеров(с единым интерфейсом):
	// билдер слов(где можно предусмотреть несколько релизаций),
	// билдер предложений(который требует билдер слов),
	// билдер абзацов(который требует билдер предложений)
	private FileCreator() {
		//closed constructor for an utility class
	}

	/**
	 * Создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
	 * @param path
	 * @param n
	 * @param size
	 * @param words массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива
	 *       в следующее предложение (1/probability).
	 * @param probability if probability == 1, then sentences in text will contain word from given array.
	 *                       if probability == 100. then 1 from 100 sentences in text will contain word from given array.
 	 */
	public static void getFiles(String path, int n, int size, String[] words, int probability) {
		StringBuilder stringBuilder = new StringBuilder();
		byte[] bytes = stringBuilder.toString().getBytes(CHARSET);



		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

		     ) {


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static StringBuilder appendLowerCaseChar(StringBuilder stringBuilder) {
		int randomIndex = RANDOM.nextInt(VALID_CHARS.length);
		return stringBuilder.append(VALID_CHARS[randomIndex]);
	}

	private static StringBuilder appendUpperCaseChar(StringBuilder stringBuilder) {
		int randomIndex = RANDOM.nextInt(VALID_CHARS.length);
		return stringBuilder.append(VALID_CHARS[randomIndex].toUpperCase());
	}

	private static StringBuilder appendWordWithProbabilityAddedFromExternalArray(StringBuilder stringBuilder,
	                                                                             int wordLength,
	                                                                             String[] words,
	                                                                             int probability) {
		int isContainWordFromArray = RANDOM.nextInt(probability);

		if (isContainWordFromArray == 0) {
			int randomIndex = RANDOM.nextInt(words.length);
			return stringBuilder.append(words[randomIndex]);
		}

		return appendWord(stringBuilder, wordLength);
	}

	private static StringBuilder appendWord(StringBuilder stringBuilder, int wordLength) {
		int currentCharsInWord = 0;
		while (currentCharsInWord < wordLength) {
			appendLowerCaseChar(stringBuilder);
			currentCharsInWord ++;
		}

		return stringBuilder;
	}

	private static StringBuilder appendWordWithCapitalLetter(StringBuilder stringBuilder, int wordLength) {
		appendUpperCaseChar(stringBuilder);
		return appendWord(stringBuilder, wordLength - 1);
	}

	private static  StringBuilder appendWordSeparator(StringBuilder stringBuilder) {
		int randomIndex = RANDOM.nextInt(WORD_SEPARATORS.length);
		return stringBuilder.append(WORD_SEPARATORS[randomIndex]);
	}

	private static StringBuilder appendSentence(StringBuilder stringBuilder, int wordsInSentence,
	                                            String[] words, int probability) {
		int charsInWord = RANDOM.nextInt(CHARS_IN_WORD) + 1;
		int currentWordsInSentence = 0;

		//first word
		appendWordWithCapitalLetter(stringBuilder, charsInWord);
		currentWordsInSentence++;

		if (wordsInSentence == 1) {
			return stringBuilder;
		}

		appendWordSeparator(stringBuilder);
		if (wordsInSentence > 2) {
			//middle words
			while (currentWordsInSentence < wordsInSentence - 1) {
				appendWordWithProbabilityAddedFromExternalArray(stringBuilder, charsInWord,
						words, probability);
				appendWordSeparator(stringBuilder);
				currentWordsInSentence++;			}
		}

		//last word
		appendWord(stringBuilder, charsInWord);
		currentWordsInSentence++;

		return stringBuilder;
	}

	private static StringBuilder appendSentenceSeparator(StringBuilder stringBuilder) {
		int randomIndex = RANDOM.nextInt(SENTENCE_SEPARATORS.length);
		return stringBuilder.append(SENTENCE_SEPARATORS[randomIndex]);
	}

	private static StringBuilder appendParagraph(StringBuilder stringBuilder, int sentencesInParagraph,
	                                             String[] words, int probability) {
		int wordsInSentence = RANDOM.nextInt(WORDS_IN_SENTENCE) + 1;
		int currentSentencesInParagraph = 0;

		while (currentSentencesInParagraph < sentencesInParagraph) {
			appendSentence(stringBuilder, wordsInSentence, words, probability);
			appendSentenceSeparator(stringBuilder);
			currentSentencesInParagraph++;
		}

		return stringBuilder;
	}
}
