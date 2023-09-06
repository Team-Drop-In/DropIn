package teamdropin.server.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.like.postLike.repository.PostLikeRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public Long createPost(Member member, Post post) {
        post.addMember(member);
        postRepository.save(post);
        return post.getId();
    }

    public Post getPost(Long id, Member member) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        post.viewCountUp();
        int likeCount =  postLikeRepository.countByPostId(post.getId());
        post.getlikeCount(likeCount);
        return post;
    }
}
