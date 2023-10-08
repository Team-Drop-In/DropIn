package teamdropin.server.domain.box.dto.box;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.dto.boxImage.BoxImageResponseDto;
import teamdropin.server.domain.review.dto.ReviewResponseDto;

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
    private Long cost;
    private Long area;
    private boolean barbellDrop;
    private String url;
    private String detail;
    private long likeCount;
    private long viewCount;
    private boolean checkBoxLike;
    private List<String> tagList;
    private List<BoxImageResponseDto> boxImageResponseDtoList;
    private List<ReviewResponseDto> reviewResponseDtoList;

}
