import java.io.*;
import java.lang.reflect.Field;
import java.util.function.BiConsumer;

/**
 * Задание 1. Необходимо разработать класс, реализующий следующие методы:
 *
 * void serialize (Object object, String file);
 *
 * Object deSerialize(String file);
 *
 * Методы выполняют сериализацию объекта Object в файл file и десериализацию объекта из этого файла.
 * Обязательна сериализация и десериализация "плоских" объектов (все поля объекта - примитивы, или String).
 *
 * @author Petrov_OlegYu
 */
public class Serializator {
	private static final String FIELD_SEPARATOR = System.lineSeparator();
	private static final String SEPARATOR = "///";

	/**
	 * Выполняет сериализацию объекта Object в файл file.
	 * Delete file content and serialize object to file.
	 * Only primitive fields and String
	 *
	 * @param object
	 * @param file
	 */
	static void serialize (Object object, File file) {
		StringBuilder stringBuilder = new StringBuilder();
		addFieldData(stringBuilder, object.getClass().getName(), "", "");

		for (Field field: object.getClass().getDeclaredFields()) {
			Class<?> type = field.getType();
			String name = field.getName();
			String typeName = type.getTypeName();
			try {
				String value = field.get(object).toString();
				addFieldData(stringBuilder, name, typeName, value);
			} catch (IllegalAccessException e) {
				System.err.println(e);
			}
		}

		try (FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos)) {
			osw.write(stringBuilder.toString());
		} catch (IOException e) {
			System.err.println("Cannot write file : " + e);
		}
	}

	private static StringBuilder addFieldData(StringBuilder stringBuilder, String name, String typeName, String value) {
		return stringBuilder.append(name)
				.append(SEPARATOR)
				.append(typeName)
				.append(SEPARATOR)
				.append(value)
				.append(FIELD_SEPARATOR);
	}



	/**
	 * Выполняет десериализацию объекта из файла.
	 *
	 * Deserialize object from file
	 * @param file
	 * @return
	 */
	static Object deSerialize(File file) {
		try (FileInputStream fis = new FileInputStream(file);
		     InputStreamReader isr = new InputStreamReader(fis);
		     BufferedReader br = new BufferedReader(isr)) {
			 Object object = createInstanceFromFirstLineString(br);
			 BiConsumer<String, Object> fieldDeserializer = createFieldDeserializer();
			 br.lines().forEach((line) -> {
			 	fieldDeserializer.accept(line, object);
			 });
			 return object;
		} catch (IOException e) {
			System.err.println("Cannot read to file : " + e);
			return null;
		}
	}

	private static BiConsumer<String, Object> createFieldDeserializer() {
		return (BiConsumer<String, Object>) (line, object) -> {
			String[] tokens = line.split(SEPARATOR);
			try {
				Field field = object.getClass().getField(tokens[0]);

				field.set(object, deserializePrimitiveFromString(tokens[1], tokens[2]));
			} catch (NoSuchFieldException e) {
				System.err.println("Field not found : " + e);
			} catch (IllegalAccessException e) {
				System.err.println(e);
			}
		};
	}

	private static Object createInstanceFromFirstLineString(BufferedReader br) throws IOException {
		String[] firstLineTokens = br.readLine().split(SEPARATOR);

		try {
			return ClassLoader.getSystemClassLoader().loadClass(firstLineTokens[0]).newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			System.err.println("Class not found or cannot be created: " + e);
			return null;
		}
	}

	private static Object deserializePrimitiveFromString(String typeName, String value) {
		switch (typeName) {
			case "boolean":
			case "java.lang.Boolean": {
				return Boolean.valueOf(value);
			}
			case "byte":
			case "java.lang.Byte": {
				return Byte.valueOf(value);
			}
			case "char":
			case "java.lang.Character": {
				return Character.valueOf(value.toCharArray()[0]);
			}
			case "short":
			case "java.lang.Short": {
				return Short.valueOf(value);
			}
			case "int":
			case "java.lang.Integer": {
				return Integer.valueOf(value);
			}
			case "long":
			case "java.lang.Long": {
				return Long.valueOf(value);
			}
			case "float":
			case "java.lang.Float": {
				return Float.valueOf(value);
			}
			case "double":
			case "java.lang.Double": {
				return Double.valueOf(value);
			}
			default: {
				return value;
			}
		}
	}
}
