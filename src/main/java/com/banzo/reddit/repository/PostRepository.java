package com.banzo.reddit.repository;

import com.banzo.reddit.model.Post;
import com.banzo.reddit.model.Subreddit;
import com.banzo.reddit.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findAllBySubreddit(Subreddit subreddit);

  List<Post> findAllByUser(User user);
}
