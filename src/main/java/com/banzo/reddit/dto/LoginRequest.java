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
public class LoginRequest {

  @NotBlank(message = "Username is required")
  String username;
  @NotBlank(message = "Password is required")
  String password;
}
