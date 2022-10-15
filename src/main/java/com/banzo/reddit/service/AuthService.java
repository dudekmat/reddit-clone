package com.banzo.reddit.service;

import com.banzo.reddit.dto.RegistrationRequest;
import com.banzo.reddit.model.User;
import com.banzo.reddit.repository.UserRepository;
import java.time.Clock;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final Clock clock;

  @Transactional
  public void signup(RegistrationRequest registrationRequest) {
    String encodedPassword = encodePassword(registrationRequest.getPassword());

    userRepository.save(User.builder()
        .username(registrationRequest.getUsername())
        .email(registrationRequest.getEmail())
        .password(encodedPassword)
        .created(Instant.now(clock))
        .enabled(false)
        .build());
  }

  private String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
}
