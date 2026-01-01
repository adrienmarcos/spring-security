package br.com.buildrun.springsecurity.controller.dto.feed;

import java.util.List;

public record FeedDto(
    List<FeedItemDto> feedItems,
    int page,
    int pageSize,
    int totalPages,
    long totalElements
) {}
