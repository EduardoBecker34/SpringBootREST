package br.com.eduardo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.model.Person;
import br.com.eduardo.services.PersonService;

@RestController
@RequestMapping("/person")
public class PersonController {

	@Autowired
	private PersonService personServices;

	@GetMapping
	public List<Person> findAll() {
		return personServices.findAll();
	}

	@GetMapping("/{id}")
	public Person findById(@PathVariable("id") Long id) {
		return personServices.findById(id);
	}

	@PostMapping
	public Person create(@RequestBody Person person) {
		return personServices.createPerson(person);
	}

	@PutMapping
	public Person update(@RequestBody Person person) {
		return personServices.updatePerson(person);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		personServices.deletePerson(id);
		return ResponseEntity.ok().build();
	}

}
