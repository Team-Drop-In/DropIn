package teamdropin.server.domain.box.dto.box;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String mainImageUrl;
    private List<String> tagList;


}
