package br.com.eduardo.services;

import java.util.List;

import javax.transaction.Transactional;

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
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		var vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}

	@Transactional //Informa ao server que o ACID devem ser observados
	public PersonVO disablePerson(Long id) {
		repository.disablePersons(id);

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		return DozerConverter.parseObject(entity, PersonVO.class);
	}

	public void deletePerson(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		repository.delete(entity);
	}
}
