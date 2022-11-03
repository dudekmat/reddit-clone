package com.banzo.reddit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationResponse {

  String authenticationToken;
  String username;
}
