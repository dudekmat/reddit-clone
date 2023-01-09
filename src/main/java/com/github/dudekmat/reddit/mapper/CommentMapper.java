package com.github.dudekmat.reddit.mapper;

import com.github.dudekmat.reddit.dto.CommentDetails;
import com.github.dudekmat.reddit.dto.CommentPayload;
import com.github.dudekmat.reddit.model.Comment;
import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.User;
import java.time.Clock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "text", source = "commentPayload.text")
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now(clock))")
  @Mapping(target = "post", source = "post")
  @Mapping(target = "user", source = "user")
  Comment mapToComment(CommentPayload commentPayload, Clock clock, Post post, User user);

  @Mapping(target = "postId", expression = "java(comment.getPost().getId())")
  @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
  CommentDetails mapToCommentDetails(Comment comment);
}
