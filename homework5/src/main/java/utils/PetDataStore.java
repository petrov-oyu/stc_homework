package utils;

import models.Person;
import models.Pet;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents class for store, add, find, change and show a pet data.
 *
 * Реализовать:
 * метод добавления животного в общий список (учесть, что добавление дубликатов должно приводить к исключительной ситуации)
 * поиск животного по его кличке (поиск должен быть эффективным)
 * изменение данных животного по его идентификатору
 * вывод на экран списка животных в отсортированном порядке. Поля для сортировки –  хозяин, кличка животного, вес.
 */
public class PetDataStore {
	private HashMap<UUID, Pet> pets;

	/**
	 * create a pet data store without duplicates by uuid
	 */
	public PetDataStore() {
		pets = new HashMap<UUID, Pet>();
	}

	/**
	 * Метод добавления животного в общий список.
	 * Добавление дубликатов приводит к исключительной ситуации
	 * @throws DuplicateException
	 */
	public void add(Pet pet) throws DuplicateException {
		if (pets.containsKey(pet.getUuid()))
			throw new DuplicateException();

		pets.put(pet.getUuid(), pet);
	}

	/**
	 * Поиск животного по его кличке.
	 * Returns set of {@link Pet} with given name.
	 * If {@link Pet} with given name not found, then returns empty set
	 */
	public Set<Pet> find(String name) {
		return pets.values().stream()
				   .filter(pet -> pet.getName().equals(name))
				   .collect(Collectors.toSet());
	}

	/**
	 * Изменение данных животного по его идентификатору
	 */
	public void changePetName(UUID petUUID, String name) {
		pets.get(petUUID).setName(name);
	}

	/**
	 * Изменение данных животного по его идентификатору
	 */
	public void changePetOwner(UUID petUUID, Person owner) {
		pets.get(petUUID).setOwner(owner);
	}

	/**
	 * Изменение данных животного по его идентификатору
	 */
	public void changePetWeight(UUID petUUID, double weight) {
		pets.get(petUUID).setWeight(weight);
	}

	/**
	 * вывод на экран списка животных в отсортированном порядке.
	 * Поля для сортировки –  хозяин, кличка животного, вес.
	 */
	public void showSortingPetsByOwner() {
		showPets((p1, p2) -> {
			return p1.getOwner().compareTo(p2.getOwner());
		});
	}

	/**
	 * вывод на экран списка животных в отсортированном порядке.
	 * Поля для сортировки –  хозяин, кличка животного, вес.
	 */
	public void showSortingPetsByName() {
		showPets((p1, p2) -> {
			return p1.getName().compareTo(p2.getName());
		});
	}

	/**
	 * вывод на экран списка животных в отсортированном порядке.
	 * Поля для сортировки –  хозяин, кличка животного, вес.
	 */
	public void showSortingPetsByWeight() {
		showPets((p1, p2) -> {
			return p1.getWeight().compareTo(p2.getWeight());
		});
	}

	/**
	 * show sorting pets by given comparator
	 */
	private void showPets(Comparator<Pet> comparator) {
		System.out.println(pets.values()
				.stream()
				.sorted(comparator)
				.toArray());
	}
}
