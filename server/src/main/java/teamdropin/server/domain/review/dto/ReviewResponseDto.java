package teamdropin.server.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String nickname;
    private String body;
    private long likeCount;
    private boolean checkLike;
    private boolean checkWriter;
    private LocalDateTime createdAt;

    public ReviewResponseDto(Long id, String nickname, String body, long likeCount, boolean checkLike, boolean checkWriter, LocalDateTime createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.body = body;
        this.likeCount = likeCount;
        this.checkLike = checkLike;
        this.checkWriter = checkWriter;
        this.createdAt = createdAt;
    }
}
