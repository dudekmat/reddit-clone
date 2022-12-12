package com.banzo.reddit.service;

import static com.banzo.reddit.model.VoteType.UPVOTE;

import com.banzo.reddit.dto.VotePayload;
import com.banzo.reddit.exception.NotFoundException;
import com.banzo.reddit.exception.RedditException;
import com.banzo.reddit.model.Post;
import com.banzo.reddit.model.Vote;
import com.banzo.reddit.model.VoteType;
import com.banzo.reddit.repository.PostRepository;
import com.banzo.reddit.repository.VoteRepository;
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
