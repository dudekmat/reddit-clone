package com.banzo.reddit.dto;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class PostPayload {

  @NotBlank(message = "Subreddit name cannot be empty")
  String subredditName;
  @NotBlank(message = "Post name cannot be empty")
  String postName;
  String url;
  String description;
}
