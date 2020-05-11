package models;

import java.util.Objects;
import java.util.UUID;

/**
 * Container for base information about Pet
 *
 * У каждого животного есть уникальный идентификационный номер,
 * кличка,
 * хозяин (объект класс Person с полями – имя, возраст, пол),
 * вес.
 */
public class Pet {
	private final UUID uuid;
	private String name;
	private Person owner;
	private double weight;

	/**
	 * Create Pet with unique identifier
	 */
	public Pet(String name, Person owner, double weight) {
		this.uuid = UUID.randomUUID();

		this.name = name;
		this.owner = owner;
		this.weight = weight;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public Pet setName(String name) {
		this.name = name;
		return this;
	}

	public Person getOwner() {
		return owner;
	}

	public Pet setOwner(Person owner) {
		this.owner = owner;
		return this;
	}

	public Double getWeight() {
		return weight;
	}

	public Pet setWeight(double weight) {
		this.weight = weight;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Pet pet = (Pet) o;
		return uuid.equals(pet.uuid);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}
}
