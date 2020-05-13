import models.Person;
import models.Person.Sex;
import models.Pet;
import org.junit.Before;
import org.junit.Test;
import utils.DuplicateException;
import utils.PetDataStore;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Разработать программу – картотеку домашних животных.
 * У каждого животного есть уникальный идентификационный номер, кличка, хозяин
 * (объект класс Person с полями – имя, возраст, пол), вес.
 *
 * Реализовать:
 *
 * метод добавления животного в общий список (учесть,
 *      что добавление дубликатов должно приводить к исключительной ситуации)
 * поиск животного по его кличке (поиск должен быть эффективным)
 * изменение данных животного по его идентификатору
 * вывод на экран списка животных в отсортированном порядке.
 *      Поля для сортировки –  хозяин, кличка животного, вес.
 */
public class PetDataStoreTest {
	private PetDataStore store;
	private Pet firstPet;
	private Pet secondPet;

	@Before
	public void setUp() {
		store = new PetDataStore();
		Person person = new Person();
		person.setAge(10)
				.setName("firstPetOwner")
				.setSex(person.new Sex(Sex.MAN));
		firstPet = new Pet("firstPet", person, 2);
		store.add(firstPet);

		person = new Person();
		person.setAge(10)
				.setName("secondPetOwner")
				.setSex(person.new Sex(Sex.MAN));
		secondPet = new Pet("secondPet", person, 3);
		store.add(secondPet);

		person = new Person();
		person.setAge(10)
				.setName("romOwner")
				.setSex(person.new Sex(Sex.MAN));
		Pet thirdPet = new Pet("rom", person, 6);
		store.add(thirdPet);

		person = new Person();
		person.setAge(10)
				.setName("whiskiyOwner")
				.setSex(person.new Sex(Sex.MAN));
		Pet fourthPet = new Pet("whiskiy", person, 2);
		store.add(fourthPet);
	}

	@Test(expected = DuplicateException.class)
	public void shouldThrowExceptionWhenTryAddedDuplicatePet() {
		store.add(firstPet);
	}

	@Test
	public void shouldFindPetByName() {
		assertEquals(secondPet, store.find("secondPet").get(0));
	}

	@Test
	public void shouldChangePetNameByUUID() {
		UUID uuid = secondPet.getUuid();
		store.changePetName(uuid, "newName");
		assertEquals(secondPet, store.find("newName").get(0));

		assertTrue(store.find("secondPet").isEmpty());
	}

	@Test
	public void shouldChangePetOwnerByUUID() {
		UUID uuid = secondPet.getUuid();

		Person person = new Person();
		store.changePetOwner(uuid, person);
		assertEquals(person, store.find("secondPet").get(0).getOwner());
	}

	@Test
	public void shouldChangePetWeightByUUID() {
		UUID uuid = secondPet.getUuid();

		store.changePetWeight(uuid, 123);
		assertEquals(123, store.find("secondPet").get(0).getWeight(), 0.01);
	}

	@Test
	public void shouldSortAndShow() {
		store.showSortingPetsByName();
		store.showSortingPetsByOwner();
		store.showSortingPetsByWeight();
	}
}
