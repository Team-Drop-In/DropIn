package teamdropin.server.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.member.dto.GetWriterResponseDto;
import teamdropin.server.domain.post.entity.Category;

import java.time.LocalDateTime;

@Data
@Builder
public class GetAllPostResponseDto {

    private Long id;
    private String title;
    private String body;
    private long viewCount;
    private Category category;
    private long likeCount;
    private GetWriterResponseDto writer;
    private long commentCount;
    private LocalDateTime createdDate;
    private String profileImageUrl;

}
