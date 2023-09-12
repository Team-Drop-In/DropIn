package teamdropin.server.domain.box.mapper;

import org.springframework.stereotype.Component;
import teamdropin.server.domain.box.dto.BoxCreateRequestDto;
import teamdropin.server.domain.box.entity.Box;

@Component
public class BoxMapper {

    public Box toEntity(BoxCreateRequestDto boxCreateRequestDto){
        return Box.builder()
                .boxName(boxCreateRequestDto.getBoxName())
                .boxLocation(boxCreateRequestDto.getBoxLocation())
                .build();
    }
}
