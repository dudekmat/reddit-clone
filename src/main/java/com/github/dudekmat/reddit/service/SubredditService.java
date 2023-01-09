package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.dto.SubredditDetails;
import com.github.dudekmat.reddit.dto.SubredditPayload;
import com.github.dudekmat.reddit.exception.NotFoundException;
import com.github.dudekmat.reddit.mapper.SubredditMapper;
import com.github.dudekmat.reddit.repository.SubredditRepository;
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
