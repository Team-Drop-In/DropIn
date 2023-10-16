package teamdropin.server.domain.box.dto.boxImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
public class BoxImageResponseDto {

    private Long id;
    private long imageIndex;
    private String boxImageUrl;

    public BoxImageResponseDto(Long id, long imageIndex, String boxImageUrl) {
        this.id = id;
        this.imageIndex = imageIndex;
        this.boxImageUrl = boxImageUrl;
    }
}
