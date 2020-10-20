package br.com.eduardo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import br.com.eduardo.model.Person;

@Service
public class PersonService {
	private final AtomicLong counter = new AtomicLong();

	public Person createPerson(Person person) {
		return person;
	}

	public Person findById(String id) {
		Person person = new Person();

		person.setId(counter.incrementAndGet());
		person.setFirstName("Eduardo");
		person.setLastName("Becker");
		person.setAddres("Avenida Brasil, Passo Fundo, RS");
		person.setGender("male");

		return person;
	}

	public List<Person> findAll() {
		List<Person> persons = new ArrayList<Person>();

		for (int i = 0; i <= 7; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}

		return persons;
	}

	private Person mockPerson(int i) {
		Person person = new Person();

		person.setId(counter.incrementAndGet());
		person.setFirstName("Person FirstName");
		person.setLastName("Person LastName");
		person.setAddres("Endereço: " + i);
		person.setGender("Male");

		return person;
	}
}
