package com.banzo.reddit.mapper;

import com.banzo.reddit.dto.PostDetails;
import com.banzo.reddit.dto.PostPayload;
import com.banzo.reddit.model.Post;
import com.banzo.reddit.model.Subreddit;
import com.banzo.reddit.model.User;
import java.time.Clock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now(clock))")
  @Mapping(target = "subreddit", source = "subreddit")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "description", source = "postPayload.description")
  Post mapToPost(PostPayload postPayload, Subreddit subreddit, User user, Clock clock);

  @Mapping(target = "id", source = "post.id")
  @Mapping(target = "subredditName", source = "subreddit.name")
  @Mapping(target = "username", source = "user.username")
  PostDetails mapToPostDetails(Post post);
}
