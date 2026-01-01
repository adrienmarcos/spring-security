package br.com.buildrun.springsecurity.service;

import br.com.buildrun.springsecurity.dto.feed.FeedDto;
import br.com.buildrun.springsecurity.dto.feed.FeedItemDto;
import br.com.buildrun.springsecurity.repository.TweetRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private final TweetRepository tweetRepository;

    public FeedService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public FeedDto getFeed(int page, int pageSize) {
        var tweets = tweetRepository.findAll(
                PageRequest.of(page, pageSize, Sort.Direction.DESC, "creationTimestamp")
        ).map(tweet -> {
            return new FeedItemDto(tweet.getTweetId(), tweet.getContent(), tweet.getUser().getUsername());
        });

        return new FeedDto(tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements());
    }
}
