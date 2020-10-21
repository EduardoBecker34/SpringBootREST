package br.com.eduardo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.eduardo.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
