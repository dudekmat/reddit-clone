package com.banzo.reddit.dto;

import javax.validation.constraints.NotBlank;
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
public class CommentPayload {

  @NotNull(message = "Post id is required")
  Long postId;
  @NotBlank(message = "Text cannot be empty")
  String text;
}
