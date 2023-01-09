package com.github.dudekmat.reddit.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_generator")
  @SequenceGenerator(name = "comment_generator", sequenceName = "comment_id_seq", allocationSize = 1)
  private Long id;

  private String text;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", referencedColumnName = "id")
  private Post post;

  private Instant createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
