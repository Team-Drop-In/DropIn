package teamdropin.server.domain.box.dto.box;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.dto.boxTag.BoxTagResponseDto;
import teamdropin.server.domain.box.entity.BoxTag;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxSearchDto {

    private Long id;
    private String name;
    private String location;
    private long likeCount;
    private long viewCount;
    private long reviewCount;
    private String mainImageUrl;
    private List<BoxTagResponseDto> tagList;

    public BoxSearchDto(Long id, String name, String location, long likeCount, long viewCount,long reviewCount, String mainImageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.reviewCount = reviewCount;
        this.mainImageUrl = mainImageUrl;
    }
}
