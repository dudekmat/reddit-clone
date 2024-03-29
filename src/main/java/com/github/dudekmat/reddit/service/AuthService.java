package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.dto.AuthenticationResponse;
import com.github.dudekmat.reddit.dto.LoginRequest;
import com.github.dudekmat.reddit.dto.RefreshTokenRequest;
import com.github.dudekmat.reddit.dto.RegistrationRequest;
import com.github.dudekmat.reddit.exception.NotFoundException;
import com.github.dudekmat.reddit.exception.VerificationException;
import com.github.dudekmat.reddit.model.NotificationEmail;
import com.github.dudekmat.reddit.model.User;
import com.github.dudekmat.reddit.model.VerificationToken;
import com.github.dudekmat.reddit.repository.UserRepository;
import com.github.dudekmat.reddit.repository.VerificationTokenRepository;
import com.github.dudekmat.reddit.security.JwtProvider;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  @Value("${server.address:localhost}")
  private String serverAddress;

  @Value("${server.port:8080}")
  private String serverPort;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Clock clock;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;
  private final JwtProvider jwtProvider;
  private final AuthenticationManager authenticationManager;
  private final RefreshTokenService refreshTokenService;

  @Transactional
  public void signup(RegistrationRequest registrationRequest) {
    String encodedPassword = encodePassword(registrationRequest.getPassword());

    User user = userRepository.save(User.builder()
        .username(registrationRequest.getUsername())
        .email(registrationRequest.getEmail())
        .password(encodedPassword)
        .created(Instant.now(clock))
        .enabled(false)
        .build());

    String token = generateVerificationToken(user);
    String activationEmailUrl = "http://" + serverAddress + ":" + serverPort
        + "/api/auth/account-verification/" + token;

    String message = mailContentBuilder.build(
        "Thank you for signing up to Reddit, please click on the below url to activate your account : "
            + activationEmailUrl);

    mailService.sendMail(
        NotificationEmail.builder()
            .subject("Please activate your account")
            .recipient(user.getEmail())
            .body(message)
            .build());
  }

  @Transactional
  public void verifyAccount(String token) {
    VerificationToken verificationToken =
        verificationTokenRepository.findByToken(token)
            .orElseThrow(() -> new VerificationException("Invalid token"));

    fetchUserAndEnable(verificationToken);
  }

  public AuthenticationResponse login(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String authenticationToken = jwtProvider.generateToken(authentication);

    return AuthenticationResponse.builder()
        .authenticationToken(authenticationToken)
        .refreshToken(refreshTokenService.generateRefreshToken())
        .expiresAt(Instant.now(clock).plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
        .username(loginRequest.getUsername())
        .build();
  }

  @Transactional(readOnly = true)
  public User getCurrentUser() {
    org.springframework.security.core.userdetails.User principal =
        (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();

    return userRepository.findByUsername(principal.getUsername())
        .orElseThrow(() -> new NotFoundException("User not found: " + principal.getUsername()));
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString();

    verificationTokenRepository.save(
        VerificationToken.builder()
            .token(token)
            .user(user)
            .build());

    return token;
  }

  private String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }

  private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("User not found: " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }

  public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
    String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());

    return AuthenticationResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(Instant.now(clock).plusMillis(jwtProvider.getJwtExpirationTimeInMillis()))
        .username(refreshTokenRequest.getUsername())
        .build();
  }

  public boolean isLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return !(authentication instanceof AnonymousAuthenticationToken)
        && authentication.isAuthenticated();
  }
}
