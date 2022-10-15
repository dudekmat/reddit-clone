package com.banzo.reddit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegistrationRequest {

  @NotBlank(message = "Username is required")
  String username;
  @Email
  @NotEmpty(message = "Email is required")
  String email;
  @NotBlank(message = "Password is required")
  String password;
}
