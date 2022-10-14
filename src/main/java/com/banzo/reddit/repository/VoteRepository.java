package com.banzo.reddit.repository;

import com.banzo.reddit.model.Post;
import com.banzo.reddit.model.User;
import com.banzo.reddit.model.Vote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

  Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
