package com.banzo.reddit.security;

import static io.jsonwebtoken.Jwts.parser;

import com.banzo.reddit.exception.RedditException;
import io.jsonwebtoken.Claims;
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

  private static final String KEYSTORE_RETRIEVING_ERROR_MESSAGE = "Exception occurred while retrieving public key from keystore";
  private static final String KEYSTORE_LOADING_ERROR_MESSAGE = "Error occurred while loading keystore";

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
      throw new RedditException(KEYSTORE_LOADING_ERROR_MESSAGE);
    }
  }

  public String generateToken(Authentication authentication) {
    User principal = (User) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(principal.getUsername())
        .signWith(getPrivateKey())
        .compact();
  }

  public boolean validateToken(String token) {
    parser().setSigningKey(getPublicKey()).parseClaimsJws(token);
    return true;
  }

  public String getUsernameFromJwt(String token) {
    Claims claims = parser()
        .setSigningKey(getPublicKey())
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  private Key getPrivateKey() {
    try {
      return keyStore.getKey("keystore", keystorePassword.toCharArray());
    } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
      throw new RedditException(KEYSTORE_RETRIEVING_ERROR_MESSAGE);
    }
  }

  private Key getPublicKey() {
    try {
      return keyStore.getCertificate("keystore").getPublicKey();
    } catch (KeyStoreException e) {
      throw new RedditException(KEYSTORE_RETRIEVING_ERROR_MESSAGE);
    }
  }
}
