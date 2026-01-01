package br.com.buildrun.springsecurity.controller;

import br.com.buildrun.springsecurity.controller.dto.tweet.TweetCreateRequest;
import br.com.buildrun.springsecurity.entities.Role;
import br.com.buildrun.springsecurity.entities.Tweet;
import br.com.buildrun.springsecurity.repository.TweetRepository;
import br.com.buildrun.springsecurity.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
public class TweetController {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetController(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> create(
            @RequestBody TweetCreateRequest createTweetDto,
            JwtAuthenticationToken authenticationToken) {

        var user = userRepository.findById(UUID.fromString(authenticationToken.getName())).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        var tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(createTweetDto.content());

        tweetRepository.save(tweet);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, JwtAuthenticationToken authenticationToken) {

        var user = userRepository.findById(UUID.fromString(authenticationToken.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var isAdmin = user.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        var tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found"));

        if (!isAdmin || !tweet.getUser().getUserId().equals(UUID.fromString(authenticationToken.getName()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        tweetRepository.delete(tweet);
        return ResponseEntity.ok().build();
    }

}
