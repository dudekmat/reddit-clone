package com.banzo.reddit.controller;

import com.banzo.reddit.dto.SubredditDetails;
import com.banzo.reddit.dto.SubredditPayload;
import com.banzo.reddit.service.SubredditService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subreddit")
@Validated
@RequiredArgsConstructor
public class SubredditController {

  private final SubredditService subredditService;

  @GetMapping
  public List<SubredditDetails> getAllSubreddits() {
    return subredditService.getAll();
  }

  @GetMapping("/{id}")
  public SubredditDetails getSubreddit(@PathVariable @NotNull Long id) {
    return subredditService.getSubreddit(id);
  }

  @PostMapping
  public SubredditDetails create(@RequestBody @Valid SubredditPayload subredditPayload) {
    return subredditService.save(subredditPayload);
  }
}