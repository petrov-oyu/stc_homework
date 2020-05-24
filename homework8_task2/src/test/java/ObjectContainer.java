import java.io.Serializable;

/**
 * Container for MyClass instances
 *
 * @author Petrov_OlegYu
 */
public class ObjectContainer implements Serializable {
	public MyClass first;
	public MyClass second;
	public MyClass third;

	public ObjectContainer() {
		first = new MyClass();
		first.myString = "first";

		second = new MyClass();
		second.myString = "second";

		third = new MyClass();
		third.myString = "third";
	}
}
