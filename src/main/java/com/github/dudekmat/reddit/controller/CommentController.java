package com.github.dudekmat.reddit.controller;

import com.github.dudekmat.reddit.dto.CommentDetails;
import com.github.dudekmat.reddit.dto.CommentPayload;
import com.github.dudekmat.reddit.service.CommentService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@Validated
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createComment(@RequestBody @Valid CommentPayload commentPayload) {
    commentService.createComment(commentPayload);
  }

  @GetMapping("/by-post/{postId}")
  public List<CommentDetails> getAllCommentsForPost(@PathVariable @NotNull Long postId) {
    return commentService.getCommentsByPost(postId);
  }

  @GetMapping("/by-user/{username}")
  public List<CommentDetails> getAllCommentsByUser(@PathVariable @NotBlank String username) {
    return commentService.getCommentsByUser(username);
  }
}
