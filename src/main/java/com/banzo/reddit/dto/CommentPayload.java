package com.banzo.reddit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommentPayload {

  @NotNull(message = "Post id is required")
  Long postId;
  @NotBlank(message = "Text cannot be empty")
  String text;
}
