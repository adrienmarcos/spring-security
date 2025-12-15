package br.com.buildrun.springsecurity.repository;

import br.com.buildrun.springsecurity.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

}
