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
	private static final int WORDS_IN_SENTENCE = 15;
	private static final int SENTENCES_IN_PARAGRAPH = 20;

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
		StringBuilder resultText = new StringBuilder();

		int sentencesInParagraph = RANDOM.nextInt(SENTENCES_IN_PARAGRAPH) + 1;
		int wordsInSentence = RANDOM.nextInt(WORDS_IN_SENTENCE) + 1;

		TextBuilder textBuilder = new TextBuilderImpl();
		textBuilder.setSize(size);
		TextBuilder paragraphBuilder = new ParagraphBuilder();
		TextBuilder sentenceBuilder = new ParagraphBuilder();
		TextBuilder wordBuilder = new ParagraphBuilder();

		textBuilder.addChild(paragraphBuilder);
		paragraphBuilder.addChild(sentenceBuilder);
		sentenceBuilder.addChild(wordBuilder);
	}

	private boolean getProbabilityResult(int probability) {
		return RANDOM.nextInt(probability) == 0;
	}

	/**
	 * Word builder which build word with random chars. Word contains from 1 to 15 chars
	 */
	class WordBuilder extends TextBuilderImpl {
		private final int CHARS_IN_WORD = 15;
		private final String[] VALID_CHARS = new String[]{"q", "w", "e", "r", "t", "y",
				"u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};

		@Override
		public StringBuilder build() {
			currentSize = 0;
			StringBuilder word = new StringBuilder();

			int charsInWord = RANDOM.nextInt(CHARS_IN_WORD) + 1;
			for (int i = 0; i < charsInWord; i++) {
				append(word, createLowerCaseChar());
			}

			return  word;
		}

		private String createLowerCaseChar() {
			int randomIndex = RANDOM.nextInt(VALID_CHARS.length);
			return  VALID_CHARS[randomIndex];
		}
	}

	/**
	 * Sentence builder which contains words as children
	 */
	class SentenceBuilder extends TextBuilderImpl {
		private final String[] WORD_SEPARATORS = new String[]{" ", ", "};

		@Override
		public StringBuilder build() {
			currentSize = 0;
			StringBuilder sentence = new StringBuilder();

			children.forEach((wordBuilder) -> {
				StringBuilder separator = new StringBuilder();

				int randomIndex = RANDOM.nextInt(WORD_SEPARATORS.length);
				if (sentence.length() != 0) {
					separator.append(WORD_SEPARATORS[randomIndex]);
				}

				wordBuilder.setSize(maxSize
						- currentSize
						- separator.toString().getBytes(CHARSET).length);

				StringBuilder word = wordBuilder.build();
				if (sentence.length() == 0) {
					//its first word, then it have to start with capitilize letter
					word = changeToFirstWordInSentence(word);
				}

				append(sentence, separator.append(word));
			});

			return sentence;
		}

		private StringBuilder changeToFirstWordInSentence(StringBuilder stringBuilder) {
			String firstLetter = stringBuilder.substring(0, 0);
			stringBuilder.replace(0,0, firstLetter.toUpperCase());
			return  stringBuilder;
		}
	}


	/**
	 * Paragraph builder which contains sentences as children
	 */
	class ParagraphBuilder extends TextBuilderImpl {
		private final String[] SENTENCE_SEPARATORS = new String[]{". ", "! ", "? "};

		@Override
		public StringBuilder build() {
			currentSize = 0;
			StringBuilder paragraph = new StringBuilder();

			int randomIndexForCloseSeparator = RANDOM.nextInt(SENTENCE_SEPARATORS.length);

			children.forEach((sentenceBuilder) -> {
				StringBuilder sentence = new StringBuilder();

				int randomIndex = RANDOM.nextInt(SENTENCE_SEPARATORS.length);
				if (paragraph.length() != 0) {
					sentence.append(SENTENCE_SEPARATORS[randomIndex]);
				}

				sentenceBuilder.setSize(maxSize
						- currentSize
						- sentence.toString().getBytes(CHARSET).length
						- SENTENCE_SEPARATORS[randomIndexForCloseSeparator].getBytes(CHARSET).length);
				sentence.append(sentenceBuilder.build());
				append(paragraph, sentence);
			});

			return  append(paragraph, SENTENCE_SEPARATORS[randomIndexForCloseSeparator]);
		}
	}

	/**
	 * Text builder which contains paragraphs as children
	 */
	class TextBuilderImpl implements TextBuilder {
		private final String PARAGRAPH_SEPARATOR = System.lineSeparator();
		protected LinkedList<TextBuilder> children = new LinkedList<>();
		protected int maxSize = 0;
		protected int currentSize = 0;

		@Override
		public void addChild(TextBuilder builder) {
			children.add(builder);
		}


		@Override
		public void setSize(int maxSize) {
			this.maxSize = maxSize;
		}

		@Override
		public StringBuilder build() {
			currentSize = 0;
			StringBuilder text = new StringBuilder();

			children.forEach((paragraphBuilder) -> {
				StringBuilder paragraph = new StringBuilder();
				if (text.length() != 0) {
					paragraph.append(PARAGRAPH_SEPARATOR);
				}

				paragraphBuilder.setSize(maxSize
						- currentSize
						- paragraph.toString().getBytes(CHARSET).length
						- PARAGRAPH_SEPARATOR.getBytes(CHARSET).length);
				paragraph.append(paragraphBuilder.build());

				append(text, paragraph);
			});

			return  append(text, PARAGRAPH_SEPARATOR);
		}

		/**
		 * if size of result text is not greater then {@link TextBuilderImpl#maxSize},
		 * then append current text and recalc {@link TextBuilderImpl#currentSize},
		 * else doesn't change current text and {@link TextBuilderImpl#currentSize}
		 * @param text current text
		 * @param addText text which append to current text
		 */
		protected StringBuilder append(StringBuilder text, String addText) {
			int sizeAfterAppend = currentSize + addText.getBytes(CHARSET).length;
			if (sizeAfterAppend > maxSize) {
				return text;
			}

			currentSize = sizeAfterAppend;
			return text.append(addText);
		}

		/**
		 * if size of result text is not greater then {@link TextBuilderImpl#maxSize},
		 * then append current text and recalc {@link TextBuilderImpl#currentSize},
		 * else doesn't change current text and {@link TextBuilderImpl#currentSize}
		 * @param text current text
		 * @param addText text which append to current text
		 */
		protected StringBuilder append(StringBuilder text, StringBuilder addText) {
			int sizeAfterAppend = currentSize + addText.toString().getBytes(CHARSET).length;
			if (sizeAfterAppend > maxSize) {
				return text;
			}

			currentSize = sizeAfterAppend;
			return text.append(addText);
		}
	}

	interface TextBuilder {
		/**
		 * Add children of this text
		 * @param builder
		 */
		void addChild(TextBuilder builder);


		/**
		 * set max bytes quantity of this text.
		 */
		void setSize(int maxSize);

		/**
		 * Build text from all children and separators between them.
		 * @return
		 */
		StringBuilder build();
	}
}
