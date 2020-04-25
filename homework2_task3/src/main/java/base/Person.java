package base;

/**
 * Container for base information about person
 *
 * @author Petrov_OlegYu
 */
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
		int resOfCompare = this.sex.compareTo(o.sex);
		if (resOfCompare != 0) {
			return resOfCompare;
		}

		//выше в списке тот, кто более старший
		resOfCompare = this.age.compareTo(o.age);
		if (resOfCompare != 0) {
			return resOfCompare;
		}

		//имена сортируются по алфавиту
		return this.name.compareTo(o.name);
	}

	/**
	 * Sex of person
	 */
	public class Sex implements Comparable<Sex>{
		public static final String MAN = "MAN";
		public static final String WOMAN = "WOMAN";

		private String sex;

		public Sex(String sex) {
			if(sex != MAN || sex !=WOMAN) {
				throw new RuntimeException("unexpected sex of person");
			}
			this.sex = sex;
		}

		public boolean isMan() {
			return this.sex.equals(MAN);
		}

		public boolean isWoman() {
			return this.sex.equals(WOMAN);
		}

		@Override
		public int compareTo(Sex o) {
			//первые идут мужчины
			if (this.isMan() && o.isWoman()) {
				return 1;
			}
			if (this.isWoman() && o.isMan()) {
				return -1;
			}
			return 0;
		}
	}
}
