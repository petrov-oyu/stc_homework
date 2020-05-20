import java.nio.charset.Charset;
import java.util.LinkedList;
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
 * Represents a class for creation text file with rules:
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
	private static final String[] VALID_CHARS = new String[]{"q", "w", "e", "r", "t", "y",
			"u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};


	//TODO это больше похоже на несколько билдеров(с единым интерфейсом):
	// билдер слов(где можно предусмотреть несколько релизаций),
	// билдер предложений(который требует билдер слов),
	// билдер абзацов(который требует билдер предложений)

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
	public void getFiles(String path, int n, int size, String[] words, int probability) {
		
		ParagraphBuilder paragraphBuilder = new ParagraphBuilder();
		SentenceBuilder sentenceBuilder = new SentenceBuilder();
		WordBuilder wordBuilder = new WordBuilder();

		StringBuilder resultText = new StringBuilder();
		StringBuilder stringBuilder = new StringBuilder();

		int charsInWord;
		int sentencesInParagraph;
		int wordsInSentence;
		
		int currentSize = 0;
		while (currentSize < size) {
			resultText.append(stringBuilder);

			paragraphBuilder.clear();
			sentencesInParagraph = RANDOM.nextInt(SENTENCES_IN_PARAGRAPH) + 1;

			for (int currentSentencesInParagraph = 0; currentSentencesInParagraph < sentencesInParagraph; currentSentencesInParagraph++) {
				sentenceBuilder.clear();
				wordsInSentence = RANDOM.nextInt(WORDS_IN_SENTENCE) + 1;

				for (int currentWordsInSentence = 0; currentWordsInSentence < wordsInSentence; currentWordsInSentence++) {
					wordBuilder.clear();
					charsInWord = RANDOM.nextInt(CHARS_IN_WORD) + 1;
					for (int currentCharsInWord = 0; currentCharsInWord < charsInWord; currentCharsInWord++) {
						wordBuilder.addChild(new StringBuilder(createLowerCaseChar()));
					}
					sentenceBuilder.addChild(wordBuilder.build());
				}

				paragraphBuilder.addChild(sentenceBuilder.build());
			}

			charsInWord =   RANDOM.nextInt(CHARS_IN_WORD) + 1;


			stringBuilder = paragraphBuilder.build();
			stringBuilder.append(System.lineSeparator()).append(System.lineSeparator());
			byte[] bytes = stringBuilder.toString().getBytes(CHARSET);

			currentSize = bytes.length;
			//System.err.println(currentSize);
		}

		System.err.println(paragraphBuilder.build());

		if (currentSize == size)
			//TODO write to file 
			return;
	}

	private StringBuilder buildFirstWordInSentence(StringBuilder stringBuilder) {
		String firstLetter = stringBuilder.substring(0, 0);
		stringBuilder.replace(0,0, firstLetter.toUpperCase());
		return  stringBuilder;
	}

	private boolean getProbabilityResult(int probability) {
		return RANDOM.nextInt(probability) == 0;
	}

	private static String createUpperCaseChar() {
		int randomIndex = RANDOM.nextInt(VALID_CHARS.length);
		return  VALID_CHARS[randomIndex].toUpperCase();
	}

	private static String createLowerCaseChar() {
		int randomIndex = RANDOM.nextInt(VALID_CHARS.length);
		return  VALID_CHARS[randomIndex];
	}

	class WordBuilder extends TextBuilderImpl {
		@Override
		public StringBuilder build() {
			StringBuilder previousWord = new StringBuilder();
			children.forEach((letter) -> {
				previousWord.append(letter);
			});

			return  previousWord;
		}
	}

	class SentenceBuilder extends TextBuilderImpl {
		private final String[] WORD_SEPARATORS = new String[]{" ", ", "};

		@Override
		public StringBuilder build() {
			StringBuilder previousWord = new StringBuilder();
			children.forEach((word) -> {
				int randomIndex = RANDOM.nextInt(WORD_SEPARATORS.length);
				if (previousWord.length() != 0) {
					previousWord.append(WORD_SEPARATORS[randomIndex]);
				}
				previousWord.append(word);
			});

			return  previousWord;
		}
	}

	class ParagraphBuilder extends TextBuilderImpl {
		private final String[] SENTENCE_SEPARATORS = new String[]{". ", "! ", "? "};

		@Override
		public StringBuilder build() {
			StringBuilder previousSentence = new StringBuilder();
			children.forEach((sentence) -> {
				int randomIndex = RANDOM.nextInt(SENTENCE_SEPARATORS.length);
				if (previousSentence.length() != 0) {
					previousSentence.append(SENTENCE_SEPARATORS[randomIndex]);
				}
				previousSentence.append(sentence);
			});

			int randomIndex = RANDOM.nextInt(SENTENCE_SEPARATORS.length);
			previousSentence.append(SENTENCE_SEPARATORS[randomIndex]);

			return  previousSentence;
		}
	}

	abstract class TextBuilderImpl implements TextBuilder {
		protected LinkedList<StringBuilder> children = new LinkedList<>();

		@Override
		public void addChild(StringBuilder stringBuilder) {
			children.add(stringBuilder);
		}

		@Override
		public LinkedList<StringBuilder> getChildren() {
			return children;
		}

		@Override
		public void clear() {
			children.clear();
		}
	}

	interface TextBuilder {
		/**
		 * Add children of this text
		 * @param stringBuilder
		 */
		void addChild(StringBuilder stringBuilder);

		/**
		 * Get all children of this text
		 * @return
		 */
		LinkedList<StringBuilder> getChildren();

		/**
		 * clear children
		 */
		void clear();

		/**
		 * Build text from all children and separators between them.
		 * @return
		 */
		StringBuilder build();
	}
}
