package teamdropin.server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.comment.repository.CommentRepository;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Long createComment(Comment comment, Member member, Long postId) {
        comment.addMember(member);
        comment.addPost(postRepository.findById(postId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.POST_NOT_FOUND)));
        commentRepository.save(comment);
        return comment.getId();
    }
}
