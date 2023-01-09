package com.github.dudekmat.reddit.repository;

import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.Subreddit;
import com.github.dudekmat.reddit.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllBySubreddit(Subreddit subreddit);

  List<Post> findAllByUser(User user);
}
