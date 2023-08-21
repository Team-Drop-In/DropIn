package teamdropin.server.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamdropin.server.domain.member.dto.MemberSignUpRequestDto;
import teamdropin.server.domain.member.dto.MemberSignUpResponseDto;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.mapper.MemberMapper;
import teamdropin.server.domain.member.service.MemberService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<MemberSignUpResponseDto> signupMember(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto){
        Member member = memberMapper.toMember(memberSignUpRequestDto);
        memberService.join(member);
        MemberSignUpResponseDto response = memberMapper.toMemberSignUpResponseDto(member);
        return ResponseEntity.created(URI.create(response.getId().toString()))
                .body(response);
    }

    @GetMapping("check-duplicate/email")
    public void checkDuplicateEmail(){

    }

    @GetMapping("check-duplicate/nickname")
    public void checkDuplicateNickname(){

    }
}
