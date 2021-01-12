package br.com.eduardo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	public List<BookVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), BookVO.class);
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
