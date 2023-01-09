package com.github.dudekmat.reddit.model;

import java.time.Instant;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class Subreddit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subreddit_generator")
  @SequenceGenerator(name = "subreddit_generator", sequenceName = "subreddit_id_seq", allocationSize = 1)
  private Long id;

  private String name;
  private String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "subreddit")
  private List<Post> posts;

  private Instant createdDate;

  @ManyToOne
  private User user;
}
