package com.banzo.reddit.dto;

import java.time.Instant;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationResponse {

  String authenticationToken;
  String username;
  String refreshToken;
  Instant expiresAt;
}
