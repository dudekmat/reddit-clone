package com.github.dudekmat.reddit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PostDetails {

  Long id;
  String subredditName;
  String postName;
  String url;
  String description;
  String username;
  Integer voteCount;
  Integer commentCount;
  String duration;
  Boolean upVoted;
  Boolean downVoted;
}
