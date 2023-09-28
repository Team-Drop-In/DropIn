package teamdropin.server.domain.box.dto.box;

import com.querydsl.core.Tuple;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.boxTag.entity.BoxTag;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxSearchDto {

    private Long id;
    private String name;
    private String location;
    private int likeCount;
    private int viewCount;
    private String mainImageUrl;

    private List<String> tagList;

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
