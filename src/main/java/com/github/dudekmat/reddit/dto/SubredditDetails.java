package com.github.dudekmat.reddit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubredditDetails {

  Long id;
  String name;
  String description;
  Integer postCount;
}
