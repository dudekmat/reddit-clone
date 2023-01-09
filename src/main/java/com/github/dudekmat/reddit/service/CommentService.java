package com.github.dudekmat.reddit.service;

import com.github.dudekmat.reddit.dto.CommentDetails;
import com.github.dudekmat.reddit.dto.CommentPayload;
import com.github.dudekmat.reddit.exception.NotFoundException;
import com.github.dudekmat.reddit.mapper.CommentMapper;
import com.github.dudekmat.reddit.model.NotificationEmail;
import com.github.dudekmat.reddit.model.User;
import com.github.dudekmat.reddit.repository.CommentRepository;
import com.github.dudekmat.reddit.repository.PostRepository;
import com.github.dudekmat.reddit.repository.UserRepository;
import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

  // TODO build post url
  private static final String POST_URL = "";
  private static final String POST_NOT_FOUND_ERROR_MESSAGE = "Post not found with id=";

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final AuthService authService;
  private final Clock clock;
  private final CommentMapper commentMapper;
  private final MailContentBuilder mailContentBuilder;
  private final MailService mailService;

  @Transactional
  public void createComment(CommentPayload commentPayload) {
    var post = postRepository.findById(commentPayload.getPostId())
        .orElseThrow(
            () -> new NotFoundException(POST_NOT_FOUND_ERROR_MESSAGE + commentPayload.getPostId()));

    commentRepository.save(
        commentMapper.mapToComment(commentPayload, clock, post, authService.getCurrentUser()));

    String message = mailContentBuilder.build(
        post.getUser().getUsername() + " posted a comment on your post." + POST_URL);

    sendCommentNotification(message, post.getUser());
  }

  @Transactional(readOnly = true)
  public List<CommentDetails> getCommentsByPost(long postId) {
    var post = postRepository.findById(postId)
        .orElseThrow(
            () -> new NotFoundException(POST_NOT_FOUND_ERROR_MESSAGE + postId));

    return commentRepository.findByPost(post)
        .stream()
        .map(commentMapper::mapToCommentDetails)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<CommentDetails> getCommentsByUser(String username) {
    var user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException(
            "User not found with username=" + username));

    return commentRepository.findAllByUser(user)
        .stream()
        .map(commentMapper::mapToCommentDetails)
        .collect(Collectors.toList());
  }

  private void sendCommentNotification(String message, User user) {
    mailService.sendMail(NotificationEmail.builder()
        .subject(user.getUsername() + " commented on your post")
        .recipient(user.getEmail())
        .body(message)
        .build());
  }
}
