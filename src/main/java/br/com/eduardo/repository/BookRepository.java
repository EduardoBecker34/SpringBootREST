package br.com.eduardo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.data.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
