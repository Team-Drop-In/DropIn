package teamdropin.server.domain.box.dto.box;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.dto.boxImage.BoxImageResponseDto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBoxResponseDto {

    private Long id;
    private String name;
    private String location;
    private String phoneNumber;
    private int cost;
    private int area;
    private boolean barbellDrop;
    private String url;
    private String detail;
    private int likeCount;
    private int viewCount;
    private boolean checkBoxLike;
    private List<BoxImageResponseDto> boxImageResponseDtoList;

}
