package com.github.dudekmat.reddit.controller;

import com.github.dudekmat.reddit.dto.PostDetails;
import com.github.dudekmat.reddit.dto.PostPayload;
import com.github.dudekmat.reddit.service.PostService;
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
@RequestMapping("/api/posts")
@Validated
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createPost(@RequestBody @Valid PostPayload postPayload) {
    postService.save(postPayload);
  }

  @GetMapping
  public List<PostDetails> getAllPosts() {
    return postService.getAllPosts();
  }

  @GetMapping("/{id}")
  public PostDetails getPost(@PathVariable @NotNull Long id) {
    return postService.getPost(id);
  }

  @GetMapping("/by-subreddit/{subredditId}")
  public List<PostDetails> getPostsBySubreddit(
      @PathVariable @NotNull Long subredditId) {
    return postService.getPostsBySubreddit(subredditId);
  }

  @GetMapping("/by-user/{username}")
  public List<PostDetails> getPostsByUsername(
      @PathVariable @NotBlank String username) {
    return postService.getPostsByUsername(username);
  }
}
