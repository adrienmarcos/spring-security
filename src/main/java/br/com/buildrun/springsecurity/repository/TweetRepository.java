package br.com.buildrun.springsecurity.repository;

import br.com.buildrun.springsecurity.entities.Tweet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    
}
