package com.banzo.reddit.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class RegistrationRequest {

  @NotBlank(message = "Username is required")
  String username;
  @Email
  @NotEmpty(message = "Email is required")
  String email;
  @NotBlank(message = "Password is required")
  String password;
}
