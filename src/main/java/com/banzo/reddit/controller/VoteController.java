package com.banzo.reddit.controller;

import com.banzo.reddit.dto.VotePayload;
import com.banzo.reddit.service.VoteService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes")
@Validated
@RequiredArgsConstructor
public class VoteController {

  private final VoteService voteService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void vote(@RequestBody @Valid VotePayload votePayload) {
    voteService.vote(votePayload);
  }
}
