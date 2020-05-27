import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Create {@link Worker} from console input
 *
 * @author Petrov_OlegYu
 */
public class WorkerCreator {

	/**
	 * Create worker from file "d:\\SomeClass.java"
	 * @return custom instance of worker or null if cannot create class instance
	 */
	public Worker create() {
		List<String> lines = new ArrayList<String>();
		String startLines = "public class SomeClass implements Worker { \n " +
				"public void doWork() { \n";
		String endLines = "\n }; " +
				"\n };";

		lines.add(startLines);
		lines.addAll(readLinesFromInput());
		lines.add(endLines);

		writeLinesToJavaFile(lines);

		compileJavaFile();

		try {
			return (Worker) new WorkerClassLoader().loadClass("SomeClass").newInstance();
		} catch (Exception e) {
			System.err.println("cannot create worker instance : " + e);
		}

		return null;
	}

	private List<String> readLinesFromInput() {
		Scanner scanner = new Scanner(System.in);

		List<String> inputLines = new ArrayList<String>();
		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			if (nextLine.trim().isEmpty()) {
				break;
			}
			inputLines.add(nextLine);
		}

		return inputLines;
	}

	private void writeLinesToJavaFile(List<String> inputLines) {
		try {
			Files.write(Paths.get("d:\\SomeClass.java"), inputLines);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void compileJavaFile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, Charset.defaultCharset());
		Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjects("d:\\SomeClass.java");
		CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits1);
		task.call();
		try {
			fileManager.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	class WorkerClassLoader extends ClassLoader {
		@Override
		protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
			if (name.equals("SomeClass")) {
				try {
					byte[] bytes = Files.readAllBytes(Paths.get("d:\\SomeClass.class"));
					return defineClass(name,bytes, 0 ,bytes.length);
				} catch (IOException e) {
					System.err.println(e);
				}
			}
			return super.loadClass(name, resolve);
		}
	}
}
