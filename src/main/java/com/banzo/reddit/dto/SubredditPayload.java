package com.banzo.reddit.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubredditPayload {

  @NotBlank(message = "Name is required")
  String name;
  @NotBlank(message = "Description is required")
  String description;
}
