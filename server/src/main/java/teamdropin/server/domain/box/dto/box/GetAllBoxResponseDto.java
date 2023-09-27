package teamdropin.server.domain.box.dto.box;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllBoxResponseDto {
    private Long id;
    private String name;
    private String mainImageUrl;
    private int likeCount;
    private int viewCount;
    private String location;
    private List<String> tagList;
}
