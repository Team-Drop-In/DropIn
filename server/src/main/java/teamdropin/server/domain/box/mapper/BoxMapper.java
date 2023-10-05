package teamdropin.server.domain.box.mapper;

import org.springframework.stereotype.Component;
//import teamdropin.server.domain.box.dto.GetBoxResponseDto;
import teamdropin.server.domain.box.dto.box.*;
import teamdropin.server.domain.box.dto.boxImage.BoxImageResponseDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.entity.BoxTag;

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

    public GetBoxResponseDto boxToGetBoxResponseDto(Box box, List<BoxImageResponseDto> boxImageResponseDtoList) {

        List<String> tagList = box.getBoxTagList().stream().map(BoxTag::getTagName).collect(Collectors.toList());

        return GetBoxResponseDto.builder()
                .id(box.getId())
                .name(box.getName())
                .location(box.getLocation())
                .boxImageResponseDtoList(boxImageResponseDtoList)
                .phoneNumber(box.getPhoneNumber())
                .cost(box.getCost())
                .area(box.getArea())
                .barbellDrop(box.isBarbellDrop())
                .likeCount(box.getBoxLikes().size())
                .viewCount(box.getViewCount())
                .url(box.getUrl())
                .detail(box.getDetail())
                .tagList(tagList)
                .build();
    }

    public GetAllBoxResponseDto boxToGetAllPostResponseDto(Box box){

        List<String> tagList = box.getBoxTagList().stream().map(BoxTag::getTagName).collect(Collectors.toList());

        List<BoxImage> boxImageList = box.getBoxImageList();
        String boxMainImage = "no_image";
        for (BoxImage boxImage : boxImageList) {
            if(boxImage.getImageIndex() == 1){
                boxMainImage = boxImage.getBoxImageUrl();
            }
        }

        return GetAllBoxResponseDto.builder()
                .id(box.getId())
                .name(box.getName())
                .mainImageUrl(boxMainImage)
                .likeCount(box.getBoxLikes().size())
                .viewCount(box.getViewCount())
                .location(box.getLocation())
                .tagList(tagList)
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

    public LikeBoxResponseDto boxToLikeBoxResponseDto(Box box){
        return LikeBoxResponseDto.builder()
                .id(box.getId())
                .name(box.getName())
                .build();
    }

    public List<LikeBoxResponseDto> boxToLikeBoxResponseDtoList(List<Box> likeBoxes){
        List<LikeBoxResponseDto> resultList = likeBoxes.stream().map(this::boxToLikeBoxResponseDto).collect(Collectors.toList());
        return resultList;
    }
}
