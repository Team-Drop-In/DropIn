package teamdropin.server.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.comment.dto.CommentResponseDto;
import teamdropin.server.domain.post.entity.Category;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class GetPostResponseDto {

    private Long id;
    private String title;
    private String body;
    private int viewCount;
    private Category category;
    private String nickname;
    private List<CommentResponseDto> comments;
    private int likeCount;
    private boolean checkPostLike;
    private LocalDateTime createdDate;

}
