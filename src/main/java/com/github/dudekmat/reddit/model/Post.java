package com.github.dudekmat.reddit.model;

import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_generator")
  @SequenceGenerator(name = "post_generator", sequenceName = "post_id_seq", allocationSize = 1)
  private Long id;

  private String postName;
  private String url;

  @Lob
  private String description;
  private Integer voteCount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  private Instant createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subreddit_id", referencedColumnName = "id")
  private Subreddit subreddit;
}
