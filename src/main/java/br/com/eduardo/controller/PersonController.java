package br.com.eduardo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.data.vo.PersonVO;
import br.com.eduardo.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin
@Api(value = "Person Endpoint", description = "Description for person", tags = { "Person Endpoint" })
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService personServices;

	//@CrossOrigin(origins = "http://localhost:8080")
	@ApiOperation(value = "Find all people recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public List<PersonVO> findAll() {
		List<PersonVO> persons = personServices.findAll();
		persons.stream().forEach(p -> {
			Link link = WebMvcLinkBuilder.linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel();
			p.add(link);
		});

		return persons;
	}

	@ApiOperation(value = "Find a person by an id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO findById(@PathVariable("id") Long id) {
		PersonVO personVO = personServices.findById(id);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		personVO.add(link);

		return personVO;
	}

	@ApiOperation(value = "Create a person")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVO = personServices.createPerson(person);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(PersonController.class).findById(personVO.getKey()))
				.withSelfRel();
		personVO.add(link);

		return personVO;
	}

	@ApiOperation(value = "Update a person")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVO = personServices.updatePerson(person);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(PersonController.class).findById(personVO.getKey()))
				.withSelfRel();
		personVO.add(link);

		return personVO;
	}

	@ApiOperation(value = "Disable a person by an id")
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO disablePerson(@PathVariable("id") Long id) {
		PersonVO personVO = personServices.disablePerson(id);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(PersonController.class).findById(id)).withSelfRel();
		personVO.add(link);

		return personVO;
	}
	
	@ApiOperation(value = "Delete a person")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		personServices.deletePerson(id);
		return ResponseEntity.ok().build();
	}

}
