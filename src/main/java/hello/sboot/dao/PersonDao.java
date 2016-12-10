package hello.sboot.dao;

import org.springframework.data.repository.CrudRepository;

import hello.sboot.entity.Person;

public interface PersonDao extends CrudRepository<Person, Long>{

}
