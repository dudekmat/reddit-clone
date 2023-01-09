package com.github.dudekmat.reddit.dto;

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
public class SubredditPayload {

  @NotBlank(message = "Name is required")
  String name;
  @NotBlank(message = "Description is required")
  String description;
}
