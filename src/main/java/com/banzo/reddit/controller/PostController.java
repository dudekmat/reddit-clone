package com.banzo.reddit.controller;

import com.banzo.reddit.dto.PostDetails;
import com.banzo.reddit.dto.PostPayload;
import com.banzo.reddit.service.PostService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@Validated
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<Void> createPost(@RequestBody @Valid PostPayload postPayload) {
    postService.save(postPayload);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping
  public ResponseEntity<List<PostDetails>> getAllPosts() {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PostDetails> getPost(@PathVariable @NotNull Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
  }

  @GetMapping("/by-subreddit/{id}")
  public ResponseEntity<List<PostDetails>> getPostsBySubreddit(
      @PathVariable @NotNull Long subredditId) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
  }

  @GetMapping("/by-user/{name}")
  public ResponseEntity<List<PostDetails>> getPostsByUsername(
      @PathVariable @NotBlank String username) {
    return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
  }
}
