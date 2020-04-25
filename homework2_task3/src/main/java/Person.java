import java.util.Comparator;

public class Person implements Comparable<Person>{
	private Integer age;
	private Sex sex;
	private String name;

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		if (age > 0 || age < 100) {
			this.age = age;
		} else {
			System.err.println("unacceptable age" + age);
		}
	}

	@Override
	public int compareTo(Person o) {
		//первые идут мужчины
		if (this.sex.isMan() && o.sex.isWoman()) {
			return 1;
		}
		if (this.sex.isWoman() && o.sex.isMan()) {
			return -1;
		}
		//выше в списке тот, кто более старший
		if (this.age > o.age) {
			return 1;
		}
		if (this.age < o.age) {
			return -1;
		}

		//имена сортируются по алфавиту

		return 0;
	}

	public class Sex {
		public static final String MAN = "MAN";
		public static final String WOMAN = "WOMAN";

		private String sex;

		public Sex(String sex) {
			this.sex = sex;
		}

		public boolean isMan() {
			return this.sex.equals(MAN);
		}

		public boolean isWoman() {
			return this.sex.equals(WOMAN);
		}
	}
}
