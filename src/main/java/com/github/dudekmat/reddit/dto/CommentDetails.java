package com.github.dudekmat.reddit.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentDetails {

  Long id;
  Long postId;
  Instant createdDate;
  String text;
  String username;
}
