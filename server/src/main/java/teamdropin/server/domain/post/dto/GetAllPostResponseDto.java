package teamdropin.server.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.post.entity.Category;

import java.time.LocalDateTime;

@Data
@Builder
public class GetAllPostResponseDto {

    private Long id;
    private String title;
    private String body;
    private int viewCount;
    private Category category;
    private int likeCount;
    private String nickname;
    private int commentCount;
    private LocalDateTime createdDate;
    private String profileImageUrl;

}
