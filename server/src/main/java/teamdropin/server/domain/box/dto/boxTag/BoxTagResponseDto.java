package teamdropin.server.domain.box.dto.boxTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxTagResponseDto {
    private String tagName;
}
