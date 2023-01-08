package com.banzo.reddit.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_generator")
  @SequenceGenerator(name = "token_generator", sequenceName = "token_id_seq", allocationSize = 1)
  private Long id;

  private String token;

  @OneToOne(fetch = FetchType.LAZY)
  private User user;

  private Instant expiryDate;
}
