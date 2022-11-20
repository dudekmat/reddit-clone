package com.banzo.reddit.mapper;

import com.banzo.reddit.dto.CommentDetails;
import com.banzo.reddit.dto.CommentPayload;
import com.banzo.reddit.model.Comment;
import com.banzo.reddit.model.Post;
import com.banzo.reddit.model.User;
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
