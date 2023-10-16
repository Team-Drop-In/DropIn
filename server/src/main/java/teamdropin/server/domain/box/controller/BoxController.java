package teamdropin.server.domain.box.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import teamdropin.server.domain.box.dto.box.*;
import teamdropin.server.domain.box.dto.boxImage.BoxImageResponseDto;
import teamdropin.server.domain.box.entity.BoxImage;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxImageMapper;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.service.BoxService;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.review.dto.ReviewResponseDto;
import teamdropin.server.domain.review.entity.Review;
import teamdropin.server.domain.review.mapper.ReviewMapper;
import teamdropin.server.global.dto.MultiResponseDto;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "4. Box API", description = "Box API Document")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BoxController {

    private final String BOX_DEFAULT_URI = "/api/box";

    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final BoxImageMapper boxImageMapper;
    private final LikeService likeService;
    private final ReviewMapper reviewMapper;


    @Operation(summary = "박스 등록 API", description = "**AccessToken이 필수입니다.** <br> **MANAGER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "박스 등록 성공 "),
            @ApiResponse(responseCode = "400", description = "박스 등록 유효성 검증에 실패한 경우 <br> 이미지 등록 갯수가 초과한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "415", description = "이미지 포맷이 틀린 경우")
    })
    @PostMapping("/box")
    public ResponseEntity<URI> createBox(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                         @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList,
                                         @RequestPart("boxCreateRequest") @Valid BoxCreateRequestDto boxCreateRequestDto) throws IOException {
        Long boxId = boxService.createBox(member, boxCreateRequestDto, multipartFileList);
        URI location = UriCreator.createUri(BOX_DEFAULT_URI, boxId);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "박스 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "박스 조회 성공 "),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "박스를 찾을 수 없는 경우")
    })
    @GetMapping("/box/{id}")
    public ResponseEntity<SingleResponseDto> getBox(@PathVariable("id") Long boxId,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal Member member){
        GetBoxResponseDto getBoxResponseDto = boxService.getBox(boxId, member);
        return new ResponseEntity<>(new SingleResponseDto<>(getBoxResponseDto), HttpStatus.OK);
    }

    @Operation(summary = "박스 검색 API", description = "추후 묘사 예정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "박스 전체 조회 및 검색 성공 "),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우")
    })
    @GetMapping("/box/search")
    public ResponseEntity<MultiResponseDto> searchBoxesPage(BoxSearchCondition condition, Pageable pageable){
        Page<BoxSearchDto> searchBoxes = boxService.getSearchBoxes(condition,pageable);
        List<BoxSearchDto> boxes = searchBoxes.getContent();
        return new ResponseEntity<>(new MultiResponseDto(boxes,searchBoxes), HttpStatus.OK);
    }

    @Operation(summary = "박스 수정 API", description = "**AccessToken이 필수입니다.** <br> **MANAGER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "박스 수정 성공 "),
            @ApiResponse(responseCode = "400", description = "박스 수정 유효성 검증에 실패한 경우 <br> 이미지 등록 갯수가 초과한 경우"),
            @ApiResponse(responseCode = "404", description = "박스가 존재하지 않는 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "415", description = "이미지 포맷이 틀린 경우")
    })
    @PutMapping("box/{id}")
    public ResponseEntity<Void> updateBox(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                          @PathVariable("id") Long boxId,
                                          @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList,
                                          @RequestPart(value = "UpdateBoxRequestDto") @Valid UpdateBoxRequestDto updateBoxRequestDto){
        boxService.updateBox(boxId, multipartFileList, updateBoxRequestDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "박스 삭제 API", description = "**AccessToken이 필수입니다.** <br> **MANAGER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "박스 삭제 성공"),
            @ApiResponse(responseCode = "204", description = "박스 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "박스가 존재하지 않는 경우")
    })
    @DeleteMapping("box/{id}")
    public ResponseEntity<Void> deleteBox(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                          @PathVariable("id") Long boxId){
        boxService.deleteBox(boxId);
        return ResponseEntity.noContent().build();
    }
}
