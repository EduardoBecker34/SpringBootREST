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

import br.com.eduardo.data.vo.PersonVO;
import br.com.eduardo.services.PersonService;

@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService personServices;

	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<PersonVO> findAll() {
		return personServices.findAll();
	}

	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO findById(@PathVariable("id") Long id) {
		return personServices.findById(id);
	}

	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO create(@RequestBody PersonVO person) {
		return personServices.createPerson(person);
	}

	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(@RequestBody PersonVO person) {
		return personServices.updatePerson(person);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		personServices.deletePerson(id);
		return ResponseEntity.ok().build();
	}

}
