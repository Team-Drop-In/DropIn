package teamdropin.server.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.member.dto.GetWriterResponseDto;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class CommentResponseDto {

    private Long id;
    private GetWriterResponseDto writer;
    private String body;
    private boolean checkCommentLike;
    private boolean checkWriter;
    private long likeCommentCount;
    private LocalDateTime createdAt;
    private String profileImageUrl;

    public CommentResponseDto(Long id, GetWriterResponseDto writer, String body, boolean checkCommentLike, boolean checkWriter, long likeCommentCount, LocalDateTime createdAt, String profileImageUrl) {
        this.id = id;
        this.writer = writer;
        this.body = body;
        this.checkCommentLike = checkCommentLike;
        this.checkWriter = checkWriter;
        this.likeCommentCount = likeCommentCount;
        this.createdAt = createdAt;
        this.profileImageUrl = profileImageUrl;
    }
}
