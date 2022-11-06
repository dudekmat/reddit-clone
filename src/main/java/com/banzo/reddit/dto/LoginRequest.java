package com.banzo.reddit.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginRequest {

  @NotBlank(message = "Username is required")
  String username;
  @NotBlank(message = "Password is required")
  String password;
}
