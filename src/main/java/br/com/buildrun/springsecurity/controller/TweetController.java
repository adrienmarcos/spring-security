package br.com.buildrun.springsecurity.controller;

import br.com.buildrun.springsecurity.dto.tweet.TweetCreateRequest;
import br.com.buildrun.springsecurity.service.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Void> create(
            @RequestBody TweetCreateRequest createTweetDto,
            JwtAuthenticationToken authenticationToken) {
        tweetService.create(createTweetDto, authenticationToken);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/tweets/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id, JwtAuthenticationToken authenticationToken) {
        tweetService.delete(id, authenticationToken);
        return ResponseEntity.ok().build();
    }

}
