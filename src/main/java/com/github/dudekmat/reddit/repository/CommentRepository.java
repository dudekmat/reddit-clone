package com.github.dudekmat.reddit.repository;

import com.github.dudekmat.reddit.model.Comment;
import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPost(Post post);

  List<Comment> findAllByUser(User user);
}
