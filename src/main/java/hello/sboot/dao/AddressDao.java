package hello.sboot.dao;

import org.springframework.data.repository.CrudRepository;

import hello.sboot.entity.Address;

public interface AddressDao extends CrudRepository<Address, Long>{

}
