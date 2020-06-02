import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

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
	private static final String SEPARATOR = "@";

	/**
	 * Выполняет сериализацию объекта Object в файл file.
	 * Delete file content and serialize object to file.
	 * Only primitive fields and String
	 *
	 * @param object
	 * @param file
	 */
	static void serialize (Object object, File file) {
		try (FileOutputStream fos = new FileOutputStream(file);
		     OutputStreamWriter osw = new OutputStreamWriter(fos)) {
			osw.write(serialize("serializedObject", object, new StringBuilder()).toString());
		} catch (IOException e) {
			System.err.println("Cannot write file : " + e);
		}
	}

	private static StringBuilder serialize (String objName, Object object, StringBuilder stringBuilder) {
		addObjectStartFrame(stringBuilder, objName, object.getClass().getName());

		for (Field field: object.getClass().getDeclaredFields()) {
			Class<?> type = field.getType();
			try {
				String name = field.getName();
				if (type.getDeclaredFields().length > 0 && !type.equals(String.class)) {
					serialize(name, field.get(object), stringBuilder);
				} else {
					String typeName = type.getTypeName();
						String value = field.get(object).toString();
						addFieldData(stringBuilder, name, typeName, value);
				}
			} catch (IllegalAccessException e) {
				System.err.println(e);
			}
		}

		return addObjectEndFrame(stringBuilder);
	}

	private static StringBuilder addFieldData(StringBuilder stringBuilder, String name, String typeName, String value) {
		return stringBuilder.append(name)
				.append(SEPARATOR)
				.append(typeName)
				.append(SEPARATOR)
				.append(value)
				.append(SEPARATOR)
				.append(FIELD_SEPARATOR);
	}

	private static StringBuilder addObjectStartFrame(StringBuilder stringBuilder, String name, String clazz) {
		return stringBuilder.append(name)
				.append(SEPARATOR)
				.append(clazz)
				.append(SEPARATOR)
				.append(SEPARATOR)
				.append(FIELD_SEPARATOR);
	}

	private static StringBuilder addObjectEndFrame(StringBuilder stringBuilder) {
		return stringBuilder
				.append(SEPARATOR)
				.append(SEPARATOR)
				.append(SEPARATOR)
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

			List<String> lines = br.lines().collect(Collectors.toList());
			ListIterator<String> iterator = lines.listIterator();

			Object object = createInstanceFromString(convertToTokens(iterator.next()));
			return deserialize(object, iterator);
		} catch (IOException e) {
			System.err.println("Cannot read to file : " + e);
			return null;
		}
	}

	private static Object deserialize(Object parent, ListIterator<String> iterator) {
		while (iterator.hasNext()) {
			String[] lineTokens = convertToTokens(iterator.next());
			if (isObjectData(lineTokens)) {
				Object innerObject = createInstanceFromString(lineTokens);
				deserialize(innerObject, iterator);
				try {
					Field field = parent.getClass().getField(lineTokens[0]);
					field.set(parent, innerObject);
				} catch (NoSuchFieldException e) {
					System.err.println("Field not found : " + e);
				} catch (IllegalAccessException e) {
					System.err.println(e);
				}
			} else if (isEndObjectData(lineTokens)) {
				break;
			} else {
				deserializeField(lineTokens, parent);
			}
		}

		return parent;
	}


	private static void deserializeField(String[] tokens, Object parent) {
		if (tokens.length < 3) {
			System.err.println("wrong file format. Expected 3 tokens in line");
		} else {
			try {
				Field field = parent.getClass().getField(tokens[0]);

				field.set(parent, deserializePrimitiveFromString(tokens[1], tokens[2]));
			} catch (NoSuchFieldException e) {
				System.err.println("Field not found : " + e);
			} catch (IllegalAccessException e) {
				System.err.println(e);
			}
		}
	}

	private static String[] convertToTokens(String line) {
		return line.split(SEPARATOR);
	}

	private static boolean isObjectData(String[] lineTokens) {
		if (lineTokens.length < 2) {
			return false;
		}

		if (lineTokens[0].isEmpty()) {
			return false;
		}

		if (lineTokens[1].isEmpty()) {
			return false;
		}

		for (int i = 2; i < lineTokens.length; i++) {
			if (!lineTokens[i].isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private static boolean isEndObjectData(String[] lineTokens) {
		if (lineTokens.length == 0) {
			return true;
		}

		for (String token : lineTokens) {
			if (!token.isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private static Object createInstanceFromString(String[] lineTokens) {
		try {
			return ClassLoader.getSystemClassLoader().loadClass(lineTokens[1]).newInstance();
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
