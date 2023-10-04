package teamdropin.server.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String nickname;
    private String body;
    private Integer likeCount;
    private boolean checkLike;
    private boolean checkWriter;
    private LocalDateTime createdAt;

}
