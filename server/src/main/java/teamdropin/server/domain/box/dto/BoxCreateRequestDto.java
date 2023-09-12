package teamdropin.server.domain.box.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxCreateRequestDto {

    private String boxName;
    private String boxLocation;
    private List<String> boxImageUrlList;

}
