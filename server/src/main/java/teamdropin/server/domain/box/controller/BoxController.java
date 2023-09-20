package teamdropin.server.domain.box.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.dto.BoxCreateRequestDto;
import teamdropin.server.domain.box.dto.GetAllBoxResponseDto;
import teamdropin.server.domain.box.dto.GetBoxResponseDto;
import teamdropin.server.domain.box.dto.UpdateBoxRequestDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.service.BoxService;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.dto.MultiResponseDto;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BoxController {

    private final String BOX_DEFAULT_URI = "/api/box";

    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final LikeService likeService;


    /**
     * 박스 등록
     */
    @PostMapping("/box")
    public ResponseEntity<URI> createBox(@AuthenticationPrincipal Member member,
                                         @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList,
                                         @RequestPart("boxCreateRequest") BoxCreateRequestDto boxCreateRequestDto) throws IOException {
        Long boxId = boxService.createBox(member, boxCreateRequestDto, multipartFileList);
        URI location = UriCreator.createUri(BOX_DEFAULT_URI, boxId);
        return ResponseEntity.created(location).build();
    }

    /**
     * 박스 단건 조회
     */
    @GetMapping("/box/{id}")
    public ResponseEntity<SingleResponseDto> getBox(@PathVariable("id") Long boxId,
                                                    @AuthenticationPrincipal Member member){
        Box box = boxService.getBox(boxId);
        GetBoxResponseDto getBoxResponseDto = boxMapper.boxToGetBoxResponseDto(box);
        getBoxResponseDto.setLikeCount(box.getBoxLikes().size());
        getBoxResponseDto.setImageInfo(new HashMap<>());
        boolean checkBoxLike = likeService.checkBoxLike(member, box.getId());
        getBoxResponseDto.setCheckBoxLike(checkBoxLike);

        if(box.getBoxImageList() != null) {
            List<BoxImage> boxImageList = box.getBoxImageList();
            HashMap<Integer, String> imageInfo = getBoxResponseDto.getImageInfo();
            for (BoxImage boxImage : boxImageList) {
                imageInfo.put(boxImage.getImageIndex(),boxImage.getBoxImageUrl());
            }
        }
        return new ResponseEntity<>(new SingleResponseDto<>(getBoxResponseDto), HttpStatus.OK);
    }

    /**
     * 박스 전체 조회
     */
    @GetMapping("/boxes")
    public ResponseEntity<MultiResponseDto> getAllBoxes(Pageable pageable){
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
        Page<Box> pageBoxes = boxService.getAllBoxes(pageable);
        List<Box> boxes = pageBoxes.getContent();
        List<GetAllBoxResponseDto> getAllPostResponseDtoList = boxMapper.boxToGetAllBoxResponseDtoList(boxes);
        return new ResponseEntity<>(new MultiResponseDto<>(getAllPostResponseDtoList,pageBoxes), HttpStatus.OK);
    }

    @PutMapping("box/{id}")
    public ResponseEntity<Void> updateBox(@AuthenticationPrincipal Member member,
                                          @PathVariable("id") Long boxId,
                                          @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList,
                                          @RequestPart(value = "UpdateBoxRequestDto") UpdateBoxRequestDto updateBoxRequestDto){
        boxService.updateBox(boxId, multipartFileList, updateBoxRequestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("box/{id}")
    public ResponseEntity<Void> deleteBox(@AuthenticationPrincipal Member member,
                                          @PathVariable("id") Long boxId){
        boxService.deleteBox(boxId);
        return ResponseEntity.noContent().build();
    }
}
