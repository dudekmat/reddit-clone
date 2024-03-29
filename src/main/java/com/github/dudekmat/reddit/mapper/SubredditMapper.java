package com.github.dudekmat.reddit.mapper;

import com.github.dudekmat.reddit.dto.SubredditDetails;
import com.github.dudekmat.reddit.dto.SubredditPayload;
import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.Subreddit;
import com.github.dudekmat.reddit.model.User;
import java.time.Clock;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubredditMapper {

  @Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
  SubredditDetails mapToSubredditDetails(Subreddit subreddit);

  default Integer mapPosts(List<Post> posts) {
    return Objects.nonNull(posts) ? posts.size() : 0;
  }

  @Mapping(target = "name", expression = "java(\"/r/\" + subredditPayload.getName())")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "createdDate", expression = "java(java.time.Instant.now(clock))")
  @Mapping(target = "id", ignore = true)
  Subreddit mapToSubreddit(SubredditPayload subredditPayload, User user, Clock clock);
}
