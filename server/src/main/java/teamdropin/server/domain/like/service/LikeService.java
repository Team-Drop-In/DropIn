package teamdropin.server.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.repository.CommentRepository;
import teamdropin.server.domain.like.dto.LikeRequestDto;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.like.entity.LikeCategory;
import teamdropin.server.domain.like.repository.LikeRepository;
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
public class LikeService {
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final BoxRepository boxRepository;

    public void like(Member member, LikeRequestDto likeRequestDto){
        if(likeRequestDto.getLikeCategory().equals(LikeCategory.POST)) {
            savePostLike(member, likeRequestDto);
        }
        if(likeRequestDto.getLikeCategory() == LikeCategory.COMMENT) {
            saveCommentLike(member, likeRequestDto);
        }
        if(likeRequestDto.getLikeCategory() == LikeCategory.BOX) {
            saveBoxLike(member, likeRequestDto);
        }
    }



    public boolean checkPostLike(Member member, Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        if(member != null && post != null) {
            Optional<Like> optionalPostLike = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
            return optionalPostLike.isPresent();
        }
        return false;
    }

    public boolean checkCommentLike(Member member, Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(member != null && comment != null) {
            Optional<Like> optionalCommentLike = likeRepository.findByMemberIdAndCommentId(member.getId(), comment.getId());
            return optionalCommentLike.isPresent();
        }
        return false;
    }

    public boolean checkBoxLike(Member member, Long boxId){
        Box box = boxRepository.findById(boxId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
        if(member != null && box != null) {
            Optional<Like> optionalBoxLike = likeRepository.findByMemberIdAndBoxId(member.getId(), box.getId());
            return optionalBoxLike.isPresent();
        }
        return false;
    }





    private void saveBoxLike(Member member, LikeRequestDto likeRequestDto) {
        Box box = boxRepository.findById(likeRequestDto.getLikeCategoryId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
        Optional<Like> optionalBoxLike = likeRepository.findByMemberIdAndBoxId(member.getId(), box.getId());
        if (optionalBoxLike.isEmpty()) {
            likeRepository.save(new Like(member, box, likeRequestDto.getLikeCategory()));
        } else {
            likeRepository.delete(optionalBoxLike.orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND)));
        }
    }

    private void saveCommentLike(Member member, LikeRequestDto likeRequestDto) {
        Comment comment = commentRepository.findById(likeRequestDto.getLikeCategoryId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Optional<Like> optionalCommentLike = likeRepository.findByMemberIdAndCommentId(member.getId(), comment.getId());
        if (optionalCommentLike.isEmpty()) {
            likeRepository.save(new Like(member, comment, likeRequestDto.getLikeCategory()));
        } else {
            likeRepository.delete(optionalCommentLike.orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND)));
        }
    }

    private void savePostLike(Member member, LikeRequestDto likeRequestDto) {
        Post post = postRepository.findById(likeRequestDto.getLikeCategoryId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND));
        Optional<Like> optionalPostLike = likeRepository.findByMemberIdAndPostId(member.getId(), post.getId());
        if (optionalPostLike.isEmpty()) {
            likeRepository.save(new Like(member, post, likeRequestDto.getLikeCategory()));
        } else {
            likeRepository.delete(optionalPostLike.orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.LIKE_NOT_FOUND)));
        }
    }
}
