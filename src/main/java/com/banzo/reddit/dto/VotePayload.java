package com.banzo.reddit.dto;

import com.banzo.reddit.model.VoteType;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VotePayload {

  @NotNull(message = "Vote type is required")
  VoteType voteType;
  @NotNull(message = "Post id is required")
  Long postId;
}
