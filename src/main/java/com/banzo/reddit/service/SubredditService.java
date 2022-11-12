package com.banzo.reddit.service;

import com.banzo.reddit.dto.SubredditDetails;
import com.banzo.reddit.dto.SubredditPayload;
import com.banzo.reddit.exception.NotFoundException;
import com.banzo.reddit.mapper.SubredditMapper;
import com.banzo.reddit.repository.SubredditRepository;
import java.time.Clock;
import java.util.List;
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
  private final SubredditMapper subredditMapper;

  @Transactional(readOnly = true)
  public List<SubredditDetails> getAll() {
    return subredditRepository.findAll()
        .stream().map(subredditMapper::mapToSubredditDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  public SubredditDetails save(SubredditPayload subredditPayload) {
    var subreddit = subredditRepository.save(
        subredditMapper.mapToSubreddit(subredditPayload, authService.getCurrentUser(), clock));
    return subredditMapper.mapToSubredditDetails(subreddit);
  }

  @Transactional(readOnly = true)
  public SubredditDetails getSubreddit(long id) {
    return subredditRepository.findById(id)
        .map(subredditMapper::mapToSubredditDetails)
        .orElseThrow(() -> new NotFoundException("Subreddit not found with id=" + id));
  }
}
