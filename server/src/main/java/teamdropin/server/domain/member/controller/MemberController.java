package teamdropin.server.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.domain.box.dto.LikeBoxResponseDto;
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
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

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
    @PostMapping("/member")
    public ResponseEntity<URI> signupMember(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto){
        Member member = memberMapper.toMember(memberSignUpRequestDto);
        Long memberId = memberService.join(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, memberId);
        return ResponseEntity.created(location).build();
    }

    /**
     * 이메일 중복 체크
     */
    @PostMapping("check-duplicate/email")
    public ResponseEntity<Void> checkDuplicateEmail(@RequestBody SignUpDuplicateCheckEmailDto signUpDuplicateCheckEmailDto){
        memberService.validateDuplicateEmail(signUpDuplicateCheckEmailDto.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 닉네임 중복 체크
     */
    @PostMapping("check-duplicate/nickname")
    public ResponseEntity<Void> checkDuplicateNickname(@RequestBody SignUpDuplicateCheckNicknameDto signUpDuplicateCheckNicknameDto){
        memberService.validateDuplicateNickname(signUpDuplicateCheckNicknameDto.getNickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 마이페이지
     */
    @GetMapping("member/my-page")
    public ResponseEntity<SingleResponseDto> toMyPage(@AuthenticationPrincipal Member member){
        Member findMember = memberService.getMember(member.getId());
        MyInfoResponseDto myInfoResponseDto = memberMapper.memberToGetMyInfoResponseDto(findMember);
        myInfoResponseDto.setWritePostCount(postService.countWritePost(member.getId()));
        myInfoResponseDto.setWriteCommentCount(commentService.countWriteComment(member.getId()));
        List<Box> likeBoxList = boxService.findLikeBoxList(member.getId());
        List<LikeBoxResponseDto> likeBoxResponseDtoList = boxMapper.boxToLikeBoxResponseDtoList(likeBoxList);
        myInfoResponseDto.setLikeBoxList(likeBoxResponseDtoList);
        return new ResponseEntity<>(new SingleResponseDto(myInfoResponseDto), HttpStatus.OK);
    }

    /**
     * 특정 회원 조회
     */
    @GetMapping("/member/{id}")
    public ResponseEntity<SingleResponseDto> getMember(@AuthenticationPrincipal Member member, @PathVariable("id") Long memberId ){
        Member findMember = memberService.getMember(memberId);
        GetMemberResponseDto getMemberResponseDto = memberMapper.memberToGetMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto(getMemberResponseDto), HttpStatus.OK);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping("/member")
    public ResponseEntity<Void> deleteMember(@AuthenticationPrincipal Member member){
        memberService.deleteMember(member.getUsername());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping(value = "/member",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateProfile(@AuthenticationPrincipal Member member,
                                              @RequestPart MemberUpdateProfileRequestDto memberUpdateProfileRequestDto,
                                              @RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        memberService.updateProfile(member.getUsername(), memberUpdateProfileRequestDto, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/member/password")
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal Member member,
                                               @RequestBody @Valid MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto){
        memberService.updatePassword(member.getUsername(), memberUpdatePasswordRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
