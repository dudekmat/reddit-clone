package com.github.dudekmat.reddit.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_generator")
  @SequenceGenerator(name = "refresh_token_generator", sequenceName = "refresh_token_id_seq", allocationSize = 1)
  private Long id;

  private String token;
  private Instant createdDate;
}
