package teamdropin.server.domain.comment.mapper;

import org.springframework.stereotype.Component;
import teamdropin.server.domain.comment.dto.CommentResponseDto;
import teamdropin.server.domain.comment.dto.CreateCommentRequestDto;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.post.mapper.PostMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment toEntity(CreateCommentRequestDto createCommentRequestDto){
        return Comment.builder()
                .body(createCommentRequestDto.getBody())
                .build();
    }

    public CommentResponseDto commentToCommentResponseDto(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .body(comment.getBody())
                .checkCommentLike(false)
                .checkWriter(false)
                .likeCommentCount(comment.getCommentLikes().size())
                .createdAt(comment.getCreatedDate())
                .profileImageUrl(comment.getMember().getProfileImageUrl())
                .build();
    }

    public List<CommentResponseDto> commentsToCommentResponseDtoList(List<Comment> comments){
        List<CommentResponseDto> resultList =
                comments.stream().map(this::commentToCommentResponseDto).collect(Collectors.toList());
        return resultList;
    }
}
