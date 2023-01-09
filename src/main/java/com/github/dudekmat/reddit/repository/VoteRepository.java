package com.github.dudekmat.reddit.repository;

import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.User;
import com.github.dudekmat.reddit.model.Vote;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

  Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
