package com.banzo.reddit.service;

import com.banzo.reddit.dto.SubredditDetails;
import com.banzo.reddit.dto.SubredditPayload;
import com.banzo.reddit.exception.NotFoundException;
import com.banzo.reddit.model.Subreddit;
import com.banzo.reddit.repository.SubredditRepository;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubredditService {

  private final SubredditRepository subredditRepository;
  private final AuthService authService;
  private final Clock clock;

  @Transactional(readOnly = true)
  public List<SubredditDetails> getAll() {
    return subredditRepository.findAll()
        .stream().map(this::mapToSubredditDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  public SubredditDetails save(SubredditPayload subredditPayload) {
    var subreddit = subredditRepository.save(mapToSubreddit(subredditPayload));
    return mapToSubredditDetails(subreddit);
  }

  @Transactional(readOnly = true)
  public SubredditDetails getSubreddit(long id) {
    return subredditRepository.findById(id)
        .map(this::mapToSubredditDetails)
        .orElseThrow(() -> new NotFoundException("Subreddit not found with id=" + id));
  }

  private SubredditDetails mapToSubredditDetails(Subreddit subreddit) {
    return SubredditDetails.builder()
        .id(subreddit.getId())
        .name(subreddit.getName())
        .description(subreddit.getDescription())
        .postCount(Objects.nonNull(subreddit.getPosts()) ? subreddit.getPosts().size() : 0)
        .build();
  }

  private Subreddit mapToSubreddit(SubredditPayload subredditPayload) {
    return Subreddit.builder()
        .name("/r/" + subredditPayload.getName())
        .description(subredditPayload.getDescription())
        .user(authService.getCurrentUser())
        .createdDate(Instant.now(clock))
        .build();
  }
}
