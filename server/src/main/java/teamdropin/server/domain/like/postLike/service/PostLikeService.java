package teamdropin.server.domain.like.postLike.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.like.postLike.repository.PostLikeRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {
    private final MemberRepository memberRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    public void like(Member member, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));

        Optional<PostLike> optionalPostLike = postLikeRepository.findByMemberIdAndPostId(member.getId(), post.getId());

        if(optionalPostLike.isEmpty()){
            postLikeRepository.save(new PostLike(member,post));
        } else {
            postLikeRepository.delete(optionalPostLike.
                    orElseThrow(() -> new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND)));
        }
    }

    public boolean checkLike(Member member, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        if(member != null && post != null) {
            Optional<PostLike> optionalPostLike = postLikeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
            if (optionalPostLike.isPresent()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
