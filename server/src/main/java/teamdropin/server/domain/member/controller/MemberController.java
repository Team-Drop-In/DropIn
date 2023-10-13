package teamdropin.server.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.domain.box.dto.box.LikeBoxResponseDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.mapper.BoxMapper;
import teamdropin.server.domain.box.service.BoxService;
import teamdropin.server.domain.comment.service.CommentService;
import teamdropin.server.domain.like.service.LikeService;
import teamdropin.server.domain.member.dto.*;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.mapper.MemberMapper;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.domain.post.service.PostService;
import teamdropin.server.global.advice.GlobalExceptionAdvice;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Tag(name = "1. Member API", description = "Member API Document")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/api/member";
    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final BoxService boxService;
    private final BoxMapper boxMapper;
    private final PostService postService;
    private final CommentService commentService;
    private final LikeService likeService;

    /**
     * 회원가입
     */
    @Operation(summary = "회원가입 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "회원가입 유효성 검증 실패 시")
    })
    @PostMapping("/member")
    public ResponseEntity<URI> signupMember(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto){
        Member member = memberMapper.toMember(memberSignUpRequestDto);
        Long memberId = memberService.join(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, memberId);
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "회원가입 시 이메일 중복체크 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복체크 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일이 있습니다")
    })
    @PostMapping("check-duplicate/email")
    public ResponseEntity<Void> checkDuplicateEmail(@RequestBody SignUpDuplicateCheckEmailDto signUpDuplicateCheckEmailDto){
        memberService.validateDuplicateEmail(signUpDuplicateCheckEmailDto.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(summary = "회원가입 시 닉네임 중복체크 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중복체크 성공"),
            @ApiResponse(responseCode = "400", description = "이미 존재하는 이메일이 있습니다")
    })
    @PostMapping("check-duplicate/nickname")
    public ResponseEntity<Void> checkDuplicateNickname(@RequestBody SignUpDuplicateCheckNicknameDto signUpDuplicateCheckNicknameDto){
        memberService.validateDuplicateNickname(signUpDuplicateCheckNicknameDto.getNickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "마이페이지 API", description = "**AccessToken이 필수입니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "마이페이지 조회 성공 <br> **AccessToken이 필수입니다.**"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우")
    })
    @GetMapping("member/my-page")
    public ResponseEntity<SingleResponseDto> toMyPage(@Parameter(hidden = true) @AuthenticationPrincipal Member member){
        Member findMember = memberService.getMember(member.getId());
        MyInfoResponseDto myInfoResponseDto = memberMapper.memberToGetMyInfoResponseDto(findMember);
        myInfoResponseDto.setWritePostCount(postService.countWritePost(member.getId()));
        myInfoResponseDto.setWriteCommentCount(commentService.countWriteComment(member.getId()));
        List<Box> likeBoxList = boxService.findLikeBoxList(member.getId());
        List<LikeBoxResponseDto> likeBoxResponseDtoList = boxMapper.boxToLikeBoxResponseDtoList(likeBoxList);
        myInfoResponseDto.setLikeBoxList(likeBoxResponseDtoList);
        return new ResponseEntity<>(new SingleResponseDto(myInfoResponseDto), HttpStatus.OK);
    }

    @Operation(summary = "회원 조회 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원인 경우")
    })
    @GetMapping("/member/{id}")
    public ResponseEntity<SingleResponseDto> getMember(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                                       @PathVariable("id") Long memberId ){
        Member findMember = memberService.getMember(memberId);
        GetMemberResponseDto getMemberResponseDto = memberMapper.memberToGetMemberResponseDto(findMember);
        getMemberResponseDto.setWritePostCount(postService.countWritePost(member.getId()));
        getMemberResponseDto.setWriteCommentCount(commentService.countWriteComment(member.getId()));
        List<Box> likeBoxList = boxService.findLikeBoxList(member.getId());
        List<LikeBoxResponseDto> likeBoxResponseDtoList = boxMapper.boxToLikeBoxResponseDtoList(likeBoxList);
        getMemberResponseDto.setLikeBoxList(likeBoxResponseDtoList);
        return new ResponseEntity<>(new SingleResponseDto(getMemberResponseDto), HttpStatus.OK);
    }

    @Operation(summary = "회원 탈퇴 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원인 경우")
    })
    @DeleteMapping("/member")
    public ResponseEntity<Void> deleteMember(@Parameter(hidden = true) @AuthenticationPrincipal Member member){
        memberService.deleteMember(member.getUsername());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "회원 수정 API", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 수정 성공"),
            @ApiResponse(responseCode = "400", description = "이미지 용량이 초과한 경우"),
            @ApiResponse(responseCode = "401", description = "인증 권한이 없거나, 유효하지 않은 JWT일 경우"),
            @ApiResponse(responseCode = "415", description = "이미지 포맷이 틀린 경우")
    })
    @PutMapping(value = "/member",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<MemberUpdateProfileResponseDto> updateProfile(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                              @RequestPart MemberUpdateProfileRequestDto memberUpdateProfileRequestDto,
                                              @RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        String profileImageUrl = memberService.updateProfile(member.getUsername(), memberUpdateProfileRequestDto, image);
        MemberUpdateProfileResponseDto memberUpdateProfileResponseDto = new MemberUpdateProfileResponseDto(profileImageUrl);
        return new ResponseEntity<>(memberUpdateProfileResponseDto,HttpStatus.OK);
    }

    @Operation(summary = "회원 비밀번호 수정", description = "**AccessToken이 필수입니다.** <br> **USER_ROLE 권한이 필요합니다.**")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공"),
            @ApiResponse(responseCode = "400", description = "비밀번호 유효성 검증 실패 <br> 현재 비밀번호와 새로운 비밀번호 일치하지 않음")
    })
    @PutMapping("/member/password")
    public ResponseEntity<Void> updatePassword(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                               @RequestBody @Valid MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto){
        memberService.updatePassword(member.getUsername(), memberUpdatePasswordRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
