package com.banzo.reddit.service;

import com.banzo.reddit.dto.PostDetails;
import com.banzo.reddit.dto.PostPayload;
import com.banzo.reddit.exception.NotFoundException;
import com.banzo.reddit.mapper.PostMapper;
import com.banzo.reddit.repository.PostRepository;
import com.banzo.reddit.repository.SubredditRepository;
import com.banzo.reddit.repository.UserRepository;
import java.time.Clock;
import java.util.List;
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
  private final PostMapper postMapper;
  private final Clock clock;

  @Transactional(readOnly = true)
  public PostDetails getPost(long id) {
    return postRepository.findById(id)
        .map(postMapper::mapToPostDetails)
        .orElseThrow(() -> new NotFoundException("Post not found with id=" + id));
  }

  @Transactional(readOnly = true)
  public List<PostDetails> getAllPosts() {
    return postRepository.findAll().stream()
        .map(postMapper::mapToPostDetails)
        .collect(Collectors.toList());
  }

  @Transactional
  public void save(PostPayload postPayload) {
    var subreddit = subredditRepository.findByName(postPayload.getSubredditName())
        .orElseThrow(() -> new NotFoundException(
            "Subreddit not found with name=" + postPayload.getSubredditName()));

    postRepository.save(
        postMapper.mapToPost(postPayload,
            subreddit,
            authService.getCurrentUser(),
            clock));
  }

  @Transactional(readOnly = true)
  public List<PostDetails> getPostsBySubreddit(long subredditId) {
    var subreddit = subredditRepository.findById(subredditId)
        .orElseThrow(() -> new NotFoundException(
            "Subreddit not found with id=" + subredditId));

    return postRepository.findAllBySubreddit(subreddit).stream()
        .map(postMapper::mapToPostDetails)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<PostDetails> getPostsByUsername(String username) {
    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(
            "User not found with username=" + username));

    return postRepository.findAllByUser(user).stream()
        .map(postMapper::mapToPostDetails)
        .collect(Collectors.toList());
  }
}
