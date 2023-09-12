package teamdropin.server.domain.box.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.domain.box.dto.BoxCreateRequestDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.service.BoxService;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.util.UriCreator;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoxController {

    private final String BOX_DEFAULT_URI = "/api/box";

    private final BoxService boxService;
    private final BoxMapper boxMapper;


    /**
     * 박스 등록
     */
    @PostMapping("/box")
    public ResponseEntity<URI> createBox(@AuthenticationPrincipal Member member,
                                          @RequestPart(value = "image", required = false) List<MultipartFile> multipartFileList,
                                          @RequestPart("boxCreateRequest") BoxCreateRequestDto boxCreateRequestDto) throws IOException {
        Box box = boxMapper.toEntity(boxCreateRequestDto);
        Long boxId = boxService.createBox(member, box, multipartFileList);
        URI location = UriCreator.createUri(BOX_DEFAULT_URI, boxId);
        return ResponseEntity.created(location).build();
    }
}
