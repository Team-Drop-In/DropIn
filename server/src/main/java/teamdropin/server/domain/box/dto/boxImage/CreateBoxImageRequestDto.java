package teamdropin.server.domain.box.dto.boxImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoxImageRequestDto {
    private int imageIndex;
    private String imageName;
}
