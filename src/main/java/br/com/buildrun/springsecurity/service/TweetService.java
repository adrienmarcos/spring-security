package br.com.buildrun.springsecurity.service;

import br.com.buildrun.springsecurity.dto.tweet.TweetCreateRequest;
import br.com.buildrun.springsecurity.entities.Role;
import br.com.buildrun.springsecurity.entities.Tweet;
import br.com.buildrun.springsecurity.repository.TweetRepository;
import br.com.buildrun.springsecurity.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public void create(TweetCreateRequest createTweetDto, JwtAuthenticationToken authenticationToken) {
        var user = userRepository.findById(UUID.fromString(authenticationToken.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(createTweetDto.content());
        tweetRepository.save(tweet);
    }

    public void delete(Long id, JwtAuthenticationToken authenticationToken) {
        var user = userRepository.findById(UUID.fromString(authenticationToken.getName()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        var isAdmin = user.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        var tweet = tweetRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tweet not found"));

        if (!isAdmin || !tweet.getUser().getUserId().equals(UUID.fromString(authenticationToken.getName()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        tweetRepository.delete(tweet);
    }

}
