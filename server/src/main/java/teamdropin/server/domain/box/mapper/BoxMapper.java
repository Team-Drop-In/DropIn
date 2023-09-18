package teamdropin.server.domain.box.mapper;

import org.springframework.stereotype.Component;
import teamdropin.server.domain.box.dto.BoxCreateRequestDto;
//import teamdropin.server.domain.box.dto.GetBoxResponseDto;
import teamdropin.server.domain.box.dto.GetAllBoxResponseDto;
import teamdropin.server.domain.box.dto.GetBoxResponseDto;
import teamdropin.server.domain.box.dto.UpdateBoxRequestDto;
import teamdropin.server.domain.box.entity.Box;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BoxMapper {

    public Box toEntity(BoxCreateRequestDto boxCreateRequestDto){
        return Box.builder()
                .name(boxCreateRequestDto.getName())
                .location(boxCreateRequestDto.getLocation())
                .phoneNumber(boxCreateRequestDto.getPhoneNumber())
                .cost(boxCreateRequestDto.getCost())
                .area(boxCreateRequestDto.getArea())
                .barbellDrop(boxCreateRequestDto.isBarbellDrop())
                .url(boxCreateRequestDto.getUrl())
                .detail(boxCreateRequestDto.getDetail())
                .build();
    }

    public GetBoxResponseDto boxToGetBoxResponseDto(Box box) {
        return GetBoxResponseDto.builder()
                .id(box.getId())
                .name(box.getName())
                .location(box.getLocation())
                .phoneNumber(box.getPhoneNumber())
                .cost(box.getCost())
                .area(box.getArea())
                .barbellDrop(box.isBarbellDrop())
                .url(box.getUrl())
                .detail(box.getDetail())
                .build();
    }

    public GetAllBoxResponseDto boxToGetAllPostResponseDto(Box box){
        return GetAllBoxResponseDto.builder()
                .id(box.getId())
                .name(box.getName())
                .mainImageUrl(box.getBoxImageList().get(0).getBoxImageUrl())
                .likeCount(box.getBoxLikes().size())
                .location(box.getLocation())
                .build();
    }
    public List<GetAllBoxResponseDto> boxToGetAllBoxResponseDtoList(List<Box> boxes){
        List<GetAllBoxResponseDto> resultList =
                boxes.stream().map(this::boxToGetAllPostResponseDto).collect(Collectors.toList());
        return resultList;
    }

    public Box toEntity(UpdateBoxRequestDto updateBoxRequestDto){
        return Box.builder()
                .id(updateBoxRequestDto.getId())
                .name(updateBoxRequestDto.getName())
                .location(updateBoxRequestDto.getLocation())
                .phoneNumber(updateBoxRequestDto.getPhoneNumber())
                .cost(updateBoxRequestDto.getCost())
                .area(updateBoxRequestDto.getArea())
                .barbellDrop(updateBoxRequestDto.isBarbellDrop())
                .url(updateBoxRequestDto.getUrl())
                .detail(updateBoxRequestDto.getDetail())
                .build();
    }
}
