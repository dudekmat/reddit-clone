package com.banzo.reddit.security;

import com.banzo.reddit.exception.RedditException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class JwtProvider {

  private KeyStore keyStore;

  @Value("${security.keystore.password:}")
  private String keystorePassword;

  @PostConstruct
  public void init() {
    try {
      keyStore = KeyStore.getInstance("JKS");
      InputStream resourceAsStream = getClass().getResourceAsStream("/keystore.jks");
      keyStore.load(resourceAsStream, keystorePassword.toCharArray());
    } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
      throw new RedditException("Error occurred while loading keystore");
    }
  }

  public String generateToken(Authentication authentication) {
    User principal = (User) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(principal.getUsername())
        .signWith(getPrivateKey())
        .compact();
  }

  private Key getPrivateKey() {
    try {
      return keyStore.getKey("keystore", keystorePassword.toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      throw new RedditException("Exception occurred while retrieving public key from keystore");
    }
  }
}
