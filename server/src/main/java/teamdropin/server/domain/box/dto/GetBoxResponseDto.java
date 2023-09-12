package teamdropin.server.domain.box.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.boxImage.BoxImage;
import teamdropin.server.domain.like.entity.Like;

import java.util.HashMap;
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
    private HashMap<Integer,String> ImageInfo = new HashMap<>();
}
