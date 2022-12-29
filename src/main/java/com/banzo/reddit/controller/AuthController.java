package com.banzo.reddit.controller;

import com.banzo.reddit.dto.AuthenticationResponse;
import com.banzo.reddit.dto.LoginRequest;
import com.banzo.reddit.dto.RefreshTokenRequest;
import com.banzo.reddit.dto.RegistrationRequest;
import com.banzo.reddit.service.AuthService;
import com.banzo.reddit.service.RefreshTokenService;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

  private final AuthService authService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/signup")
  public void signup(@RequestBody @Valid RegistrationRequest registrationRequest) {
    authService.signup(registrationRequest);
  }

  @GetMapping("/account-verification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable @NotBlank String token) {
    authService.verifyAccount(token);
    return ResponseEntity.ok().body("Account activated successfully");
  }

  @PostMapping("/login")
  public AuthenticationResponse login(@RequestBody @Valid LoginRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("/refresh-token")
  public AuthenticationResponse refreshToken(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    return authService.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(
      @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully.");
  }
}
