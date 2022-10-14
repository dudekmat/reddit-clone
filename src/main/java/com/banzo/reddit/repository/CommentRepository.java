package com.banzo.reddit.repository;

import com.banzo.reddit.model.Comment;
import com.banzo.reddit.model.Post;
import com.banzo.reddit.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPost(Post post);

  List<Comment> findByUser(User user);
}
