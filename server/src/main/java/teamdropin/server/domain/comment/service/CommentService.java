package teamdropin.server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.comment.dto.CreateCommentRequestDto;
import teamdropin.server.domain.comment.dto.UpdateCommentRequestDto;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.mapper.CommentMapper;
import teamdropin.server.domain.comment.repository.CommentRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public Long createComment(CreateCommentRequestDto createCommentRequestDto, Member member, Long postId) {

        Comment comment = commentMapper.toEntity(createCommentRequestDto);

        comment.addMember(member);
        comment.addPost(postRepository.findById(postId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)));
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, UpdateCommentRequestDto updateCommentRequestDto, Member member) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(!comment.getMember().getId().equals(member.getId())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_AUTHORIZED);
        }
        comment.updateCommentInfo(updateCommentRequestDto.getBody());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Member member) {
        Comment comment = commentRepository.findByPostIdAndMemberIdAndId(postId, member.getId(), commentId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        if(!comment.getMember().getId().equals(member.getId())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_AUTHORIZED);
        }
        commentRepository.delete(comment);
    }

}
