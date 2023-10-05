package teamdropin.server.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String nickname;
    private String body;
    private boolean checkCommentLike;
    private boolean checkWriter;
    private int likeCommentCount;
    private LocalDateTime createdAt;
    private String profileImageUrl;

}
