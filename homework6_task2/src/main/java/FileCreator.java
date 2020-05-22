import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	private static final Random RANDOM = new Random();
	private static final int WORDS_IN_SENTENCE = 15;
	private static final int SENTENCES_IN_PARAGRAPH = 20;

	/**
	 * Создаст n файлов размером size в каталоге path. words - массив слов, probability - вероятность.
	 * @param path path of files
	 * @param n files quantity
	 * @param size file size on bytes
	 * @param words массив слов 1<=n4<=1000. Есть вероятность probability вхождения одного из слов этого массива
	 *       в следующее предложение (1/probability).
	 * @param probability if probability == 1, then sentences in text will contain word from given array.
	 *                       if probability == 100. then 1 from 100 sentences in text will contain word from given array.
 	 */
	public void getFiles(String path, int n, int size, String[] words, int probability) {
		StringBuilder resultText = new StringBuilder();

		int sentencesInParagraph;
		int wordsInSentence;

		while (resultText.toString().getBytes(CHARSET).length < size) {
			Builder textBuilder = new TextBuilder();
			textBuilder.setMaxSize(size - resultText.toString().getBytes(CHARSET).length);
			Builder paragraphBuilder = new ParagraphBuilder();
			Builder sentenceBuilder = new SentenceBuilder();
			Builder wordBuilder = new WordBuilder();
			Builder userWordBuilder = new BuilderImpl() {
				@Override
				public StringBuilder build() {
					currentSize = 0;
					String userWord = words[RANDOM.nextInt(words.length)];
					return append(new StringBuilder(), userWord);
				}
			};

			textBuilder.addChild(paragraphBuilder);
			sentencesInParagraph = RANDOM.nextInt(SENTENCES_IN_PARAGRAPH) + 1;
			for (int i = 0; i < sentencesInParagraph; i++) {
				paragraphBuilder.addChild(sentenceBuilder);
			}
			wordsInSentence = RANDOM.nextInt(WORDS_IN_SENTENCE) + 1;
			if (getProbabilityResult(probability)) {
				sentenceBuilder.addChild(userWordBuilder);
			} else {
				sentenceBuilder.addChild(wordBuilder);
			}
			for (int i = 1; i < wordsInSentence; i++) {
				sentenceBuilder.addChild(wordBuilder);
			}

			resultText.append(textBuilder.build());
		}

		for (int i = 0; i < n; i++) {
			File file = new File(path.concat("File")
					.concat(String.valueOf(i)
					.concat(".txt")));
			if (!new File(path).mkdirs()) {
				System.err.println("directory doesnt created");
			}

			try (FileWriter fw = new FileWriter(file);
			     BufferedWriter bw = new BufferedWriter(fw)) {
				bw.write(resultText.toString());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private boolean getProbabilityResult(int probability) {
		return RANDOM.nextInt(probability) == 0;
	}

	/**
	 * Word builder which build word with random chars. Word contains from 1 to 15 chars
	 */
	static class WordBuilder extends BuilderImpl {
		private final String[] VALID_CHARS = new String[]{"q", "w", "e", "r", "t", "y",
				"u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};

		@Override
		public StringBuilder build() {
			currentSize = 0;
			StringBuilder word = new StringBuilder();

			int charsInWord = RANDOM.nextInt(15) + 1;
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
	static class SentenceBuilder extends BuilderImpl {
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

				wordBuilder.setMaxSize(maxSize
						- currentSize
						- separator.toString().getBytes(CHARSET).length);

				StringBuilder word = wordBuilder.build();
				if (sentence.length() == 0) {
					//its first word, then it have to start with capitalize letter
					capitalizeFirstLetter(word);
				}

				append(sentence, separator.append(word));
			});

			return sentence;
		}

		private void capitalizeFirstLetter(StringBuilder stringBuilder) {
			if (!stringBuilder.toString().isEmpty()) {
				String firstLetter = stringBuilder.substring(0, 1);
				stringBuilder.replace(0,1, firstLetter.toUpperCase());
			}
		}
	}


	/**
	 * Paragraph builder which contains sentences as children
	 */
	static class ParagraphBuilder extends BuilderImpl {
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

				sentenceBuilder.setMaxSize(maxSize
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
	static class TextBuilder extends BuilderImpl {
		private final String PARAGRAPH_SEPARATOR = System.lineSeparator();

		@Override
		public StringBuilder build() {
			currentSize = 0;
			StringBuilder text = new StringBuilder();

			children.forEach((paragraphBuilder) -> {
				StringBuilder paragraph = new StringBuilder();
				if (text.length() != 0) {
					paragraph.append(PARAGRAPH_SEPARATOR);
				}

				paragraphBuilder.setMaxSize(maxSize
						- currentSize
						- paragraph.toString().getBytes(CHARSET).length
						- PARAGRAPH_SEPARATOR.getBytes(CHARSET).length);
				paragraph.append(paragraphBuilder.build());

				append(text, paragraph);
			});

			return append(text, PARAGRAPH_SEPARATOR);
		}
	}

	/**
	 * Builder implementation
	 */
	abstract static class BuilderImpl implements Builder {
		protected LinkedList<Builder> children = new LinkedList<>();
		protected int maxSize = 0;
		protected int currentSize = 0;

		@Override
		public void addChild(Builder builder) {
			children.add(builder);
		}


		@Override
		public void setMaxSize(int maxSize) {
			this.maxSize = maxSize;
		}

		/**
		 * if size of result text is not greater then {@link BuilderImpl#maxSize},
		 * then append current text and recalc {@link BuilderImpl#currentSize},
		 * else doesn't change current text and {@link BuilderImpl#currentSize}
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
		 * if size of result text is not greater then {@link BuilderImpl#maxSize},
		 * then append current text and recalc {@link BuilderImpl#currentSize},
		 * else doesn't change current text and {@link BuilderImpl#currentSize}
		 * @param text current text
		 * @param addText text which append to current text
		 */
		protected void append(StringBuilder text, StringBuilder addText) {
			int sizeAfterAppend = currentSize + addText.toString().getBytes(CHARSET).length;
			if (sizeAfterAppend > maxSize) {
				return;
			}

			currentSize = sizeAfterAppend;
			text.append(addText);
		}
	}

	/**
	 * Interface for builders which have children as builders
	 */
	interface Builder {
		/**
		 * Add children of this text
		 * @param builder child builder
		 */
		void addChild(Builder builder);


		/**
		 * set max bytes quantity of this text.
		 */
		void setMaxSize(int maxSize);

		/**
		 * Build text from all children and separators between them.
		 * Text will have size less or equals maxSize
		 * @return result text
		 */
		StringBuilder build();
	}
}
