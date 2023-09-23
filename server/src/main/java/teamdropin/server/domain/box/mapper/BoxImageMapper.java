package teamdropin.server.domain.box.mapper;

import org.springframework.stereotype.Component;
import teamdropin.server.domain.box.dto.boxImage.BoxImageResponseDto;
import teamdropin.server.domain.box.entity.BoxImage;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoxImageMapper {

    public BoxImageResponseDto boxImageToBoxImageResponseDto(BoxImage boxImage){
        return BoxImageResponseDto.builder()
                .id(boxImage.getId())
                .imageIndex(boxImage.getImageIndex())
                .boxImageUrl(boxImage.getBoxImageUrl())
                .build();
    }

    public List<BoxImageResponseDto> boxImageListToBoxImageResponseDtoList(List<BoxImage> boxImageList){
        return boxImageList.stream().map(this::boxImageToBoxImageResponseDto).collect(Collectors.toList());
    }
}
