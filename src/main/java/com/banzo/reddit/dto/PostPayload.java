package com.banzo.reddit.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostPayload {

  @NotBlank(message = "Subreddit name cannot be empty")
  String subredditName;
  @NotBlank(message = "Post name cannot be empty")
  String postName;
  String url;
  String description;
}
