package com.github.dudekmat.reddit.service;

import static com.github.dudekmat.reddit.model.VoteType.UPVOTE;

import com.github.dudekmat.reddit.dto.VotePayload;
import com.github.dudekmat.reddit.exception.NotFoundException;
import com.github.dudekmat.reddit.exception.RedditException;
import com.github.dudekmat.reddit.model.Post;
import com.github.dudekmat.reddit.model.Vote;
import com.github.dudekmat.reddit.model.VoteType;
import com.github.dudekmat.reddit.repository.PostRepository;
import com.github.dudekmat.reddit.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {

  private final VoteRepository voteRepository;
  private final PostRepository postRepository;
  private final AuthService authService;

  @Transactional
  public void vote(VotePayload votePayload) {
    var post = postRepository.findById(votePayload.getPostId())
        .orElseThrow(
            () -> new NotFoundException("Post not found with id=" + votePayload.getPostId()));
    var voteType = votePayload.getVoteType();

    voteRepository.findTopByPostAndUserOrderByIdDesc(post, authService.getCurrentUser())
        .ifPresent(previousVote -> checkDoubleVote(previousVote, voteType));

    if (UPVOTE.equals(voteType)) {
      post.setVoteCount(post.getVoteCount() + 1);
    } else {
      post.setVoteCount(post.getVoteCount() - 1);
    }

    voteRepository.save(mapToVote(votePayload, post));
    postRepository.save(post);
  }

  private void checkDoubleVote(Vote previousVote, VoteType newVoteType) {
    if (newVoteType.equals(previousVote.getVoteType())) {
      throw new RedditException("You have already "
          + newVoteType + "'d for this post");
    }
  }

  private Vote mapToVote(VotePayload votePayload, Post post) {
    return Vote.builder()
        .voteType(votePayload.getVoteType())
        .post(post)
        .user(authService.getCurrentUser())
        .build();
  }
}
