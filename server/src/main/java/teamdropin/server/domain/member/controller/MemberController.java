package teamdropin.server.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.member.dto.*;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.mapper.MemberMapper;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.global.dto.SingleResponseDto;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;
import teamdropin.server.global.util.UriCreator;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class MemberController {
    private final static String MEMBER_DEFAULT_URL = "/api/members";
    private final MemberService memberService;
    private final MemberMapper memberMapper;

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

    @GetMapping("check-duplicate/email")
    public ResponseEntity<Void> checkDuplicateEmail(@RequestBody SignUpCheckEmailDto signUpCheckEmailDto){
        memberService.validateDuplicateEmail(signUpCheckEmailDto.getUsername());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("check-duplicate/nickname")
    public ResponseEntity<Void> checkDuplicateNickname(@RequestBody SignUpCheckNicknameDto signUpCheckNicknameDto){
        memberService.validateDuplicateNickname(signUpCheckNicknameDto.getNickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("member/my-page")
    public ResponseEntity<SingleResponseDto> toMyPage(@AuthenticationPrincipal Member member){
        if(member == null){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_FOUND);
        }
        Member findMember = memberService.getMember(member.getId());
        MyInfoResponseDto myInfoResponseDto = memberMapper.memberToGetMyInfoResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto(myInfoResponseDto), HttpStatus.OK);
    }

    @GetMapping("/member/{id}")
    public ResponseEntity<SingleResponseDto> getMember(@PathVariable("id") Long memberId ){
        Member findMember = memberService.getMember(memberId);
        GetMemberResponseDto getMemberResponseDto = memberMapper.memberToGetMemberResponseDto(findMember);
        return new ResponseEntity<>(new SingleResponseDto(getMemberResponseDto), HttpStatus.OK);
    }
}
