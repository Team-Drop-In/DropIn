package teamdropin.server.domain.box.dto.boxImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxImageResponseDto {

    private Long id;
    private int imageIndex;
    private String boxImageUrl;

}
