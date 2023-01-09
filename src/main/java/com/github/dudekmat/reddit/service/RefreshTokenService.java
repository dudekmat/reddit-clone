package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.exception.RedditException;
import com.github.dudekmat.reddit.model.RefreshToken;
import com.github.dudekmat.reddit.repository.RefreshTokenRepository;
import java.time.Clock;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final Clock clock;

  String generateRefreshToken() {
    RefreshToken refreshToken = refreshTokenRepository.save(
        RefreshToken.builder()
            .token(UUID.randomUUID().toString())
            .createdDate(Instant.now(clock))
            .build());

    return refreshToken.getToken();
  }

  void validateRefreshToken(String token) {
    refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new RedditException("Invalid refresh token"));
  }

  public void deleteRefreshToken(String token) {
    refreshTokenRepository.deleteByToken(token);
  }
}
