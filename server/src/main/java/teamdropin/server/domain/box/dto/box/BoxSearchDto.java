package teamdropin.server.domain.box.dto.box;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoxSearchDto {

    private Long id;
    private String name;
    private String location;
    private int likeCount;
    private int viewCount;
    private String mainImageUrl;

    @QueryProjection
    public BoxSearchDto(Long id, String name, String location, int likeCount,int viewCount, String mainImageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.likeCount = likeCount;
        this.viewCount = viewCount;
        this.mainImageUrl = mainImageUrl;
    }
}
