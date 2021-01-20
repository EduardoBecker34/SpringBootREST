package br.com.eduardo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.eduardo.data.vo.BookVO;
import br.com.eduardo.data.vo.PersonVO;
import br.com.eduardo.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Book Endpoint", description = "Book endpoint description", tags = { "Book Endpoint" })
@RestController
@RequestMapping("/api/book/v1")
public class BookController {

	@Autowired
	private BookService bookServices;

	@ApiOperation(value = "Find all books recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<CollectionModel<BookVO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		//Compara String ignorando case e atribui o Sort Direction conforme parâmetro
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		Page<BookVO> books = bookServices.findAll(pageable);
		
		books.stream().forEach(p -> {
			Link link = WebMvcLinkBuilder.linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel();
			p.add(link);
		});
		
		return ResponseEntity.ok(CollectionModel.of(books));
	}

	@ApiOperation(value = "Find a book by an id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public BookVO findById(@PathVariable("id") Long id) {
		BookVO bookVO = bookServices.findById(id);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(BookController.class).findById(id)).withSelfRel();
		bookVO.add(link);

		return bookVO;
	}

	@ApiOperation(value = "Create a book")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {"application/json", "application/xml", "application/x-yaml" })
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVO = bookServices.createBook(book);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(BookController.class).findById(bookVO.getKey()))
				.withSelfRel();
		bookVO.add(link);

		return bookVO;
	}

	@ApiOperation(value = "Update a book")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {"application/json", "application/xml", "application/x-yaml" })
	public BookVO update(@RequestBody BookVO book) {
		BookVO bookVO = bookServices.updateBook(book);

		Link link = WebMvcLinkBuilder.linkTo(methodOn(BookController.class).findById(bookVO.getKey()))
				.withSelfRel();
		bookVO.add(link);

		return bookVO;
	}

	@ApiOperation(value = "Delete a book")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		bookServices.deleteBook(id);
		return ResponseEntity.ok().build();
	}

}
