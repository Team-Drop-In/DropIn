package teamdropin.server.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.member.dto.GetWriterResponseDto;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private GetWriterResponseDto writer;
    private String body;
    private long likeCount;
    private boolean checkLike;
    private boolean checkWriter;
    private LocalDateTime createdAt;

    public ReviewResponseDto(Long id, GetWriterResponseDto writer, String body, long likeCount, boolean checkLike, boolean checkWriter, LocalDateTime createdAt) {
        this.id = id;
        this.writer = writer;
        this.body = body;
        this.likeCount = likeCount;
        this.checkLike = checkLike;
        this.checkWriter = checkWriter;
        this.createdAt = createdAt;
    }
}
