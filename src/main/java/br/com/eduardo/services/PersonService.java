package br.com.eduardo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eduardo.converter.DozerConverter;
import br.com.eduardo.data.model.Person;
import br.com.eduardo.data.vo.PersonVO;
import br.com.eduardo.exception.ResourceNotFoundException;
import br.com.eduardo.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;

	public PersonVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));
		
		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO createPerson(PersonVO person) {
		var entity = DozerConverter.parseObject(person, Person.class);
		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	public PersonVO updatePerson(PersonVO person) {
		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
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
