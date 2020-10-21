package br.com.eduardo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.exception.ResourceNotFoundException;
import br.com.eduardo.model.Person;
import br.com.eduardo.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public Person findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));
	}

	public List<Person> findAll() {
		return repository.findAll();
	}

	public Person createPerson(Person person) {
		repository.save(person);
		return person;
	}

	public Person updatePerson(Person person) {
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddres(person.getAddres());
		entity.setGender(person.getGender());

		return repository.save(entity);
	}

	public void deletePerson(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		repository.delete(entity);
	}

	/*
	 * public Person findById(String id) { Person person = new Person();
	 * 
	 * person.setId(counter.incrementAndGet()); person.setFirstName("Eduardo");
	 * person.setLastName("Becker");
	 * person.setAddres("Avenida Brasil, Passo Fundo, RS");
	 * person.setGender("male");
	 * 
	 * return person; }
	 */

	/*
	 * public List<Person> findAll() { List<Person> persons = new
	 * ArrayList<Person>();
	 * 
	 * // Mock do objeto para testes
	 * 
	 * for (int i = 0; i <= 7; i++) { Person person = mockPerson(i);
	 * persons.add(person); }
	 * 
	 * return persons; }
	 */

	/*
	 * private Person mockPerson(int i) { Person person = new Person();
	 * 
	 * person.setId(counter.incrementAndGet());
	 * person.setFirstName("Person FirstName");
	 * person.setLastName("Person LastName"); person.setAddres("Endereço: " + i);
	 * person.setGender("Male");
	 * 
	 * return person; }
	 */
}
