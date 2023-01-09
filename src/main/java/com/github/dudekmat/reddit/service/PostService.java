package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.dto.PostDetails;
import com.github.dudekmat.reddit.dto.PostPayload;
import com.github.dudekmat.reddit.exception.NotFoundException;
import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.Subreddit;
import com.github.dudekmat.reddit.repository.CommentRepository;
import com.github.dudekmat.reddit.repository.PostRepository;
import com.github.dudekmat.reddit.repository.SubredditRepository;
import com.github.dudekmat.reddit.repository.UserRepository;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final SubredditRepository subredditRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final Clock clock;
  private final CommentRepository commentRepository;

  @Transactional(readOnly = true)
  public PostDetails getPost(long id) {
    return postRepository.findById(id)
        .map(this::mapToPostDetails)
        .orElseThrow(() -> new NotFoundException("Post not found with id=" + id));
  }

  @Transactional(readOnly = true)
  public List<PostDetails> getAllPosts() {
    return postRepository.findAll().stream()
        .map(this::mapToPostDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  public void save(PostPayload postPayload) {
    var subreddit = subredditRepository.findByName(postPayload.getSubredditName())
        .orElseThrow(() -> new NotFoundException(
            "Subreddit not found with name=" + postPayload.getSubredditName()));

    postRepository.save(mapToPost(postPayload, subreddit));
  }

  @Transactional(readOnly = true)
  public List<PostDetails> getPostsBySubreddit(long subredditId) {
    var subreddit = subredditRepository.findById(subredditId)
        .orElseThrow(() -> new NotFoundException(
            "Subreddit not found with id=" + subredditId));

    return postRepository.findAllBySubreddit(subreddit).stream()
        .map(this::mapToPostDetails)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostDetails> getPostsByUsername(String username) {
    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(
            "User not found with username=" + username));

    return postRepository.findAllByUser(user).stream()
        .map(this::mapToPostDetails)
        .collect(Collectors.toList());
  }

  private PostDetails mapToPostDetails(Post post) {
    return PostDetails.builder()
        .id(post.getId())
        .subredditName(post.getSubreddit().getName())
        .postName(post.getPostName())
        .url(post.getUrl())
        .description(post.getDescription())
        .username(post.getUser().getUsername())
        .voteCount(post.getVoteCount())
        .commentCount(
            Objects.nonNull(commentRepository.findByPost(post)) ? commentRepository.findByPost(post)
                .size() : 0)
        .duration(TimeAgo.using(post.getCreatedDate().toEpochMilli()))
        .build();
  }

  private Post mapToPost(PostPayload postPayload, Subreddit subreddit) {
    return Post.builder()
        .postName(postPayload.getPostName())
        .url(postPayload.getUrl())
        .description(postPayload.getDescription())
        .voteCount(0)
        .user(authService.getCurrentUser())
        .createdDate(Instant.now(clock))
        .subreddit(subreddit)
        .build();
  }
}
