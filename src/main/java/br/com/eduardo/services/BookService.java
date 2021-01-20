package br.com.eduardo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.eduardo.converter.DozerConverter;
import br.com.eduardo.data.model.Book;
import br.com.eduardo.data.vo.BookVO;
import br.com.eduardo.exception.ResourceNotFoundException;
import br.com.eduardo.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repository;

	public BookVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public Page<BookVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable); // Retorna uma lista de Pages + .getContent transforma em um List
		return page.map(this::convertToBookVO);
	}

	private BookVO convertToBookVO(Book entity) {
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public BookVO createBook(BookVO book) {
		var entity = DozerConverter.parseObject(book, Book.class);
		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public BookVO updateBook(BookVO book) {
		var entity = repository.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());

		var vo = DozerConverter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}

	public void deleteBook(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com este ID."));

		repository.delete(entity);
	}
}
