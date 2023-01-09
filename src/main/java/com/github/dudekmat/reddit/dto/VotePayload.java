package com.github.dudekmat.reddit.dto;

import com.github.dudekmat.reddit.model.VoteType;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class VotePayload {

  @NotNull(message = "Vote type is required")
  VoteType voteType;
  @NotNull(message = "Post id is required")
  Long postId;
}
