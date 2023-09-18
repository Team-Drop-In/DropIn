package teamdropin.server.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.aws.service.S3Uploader;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.member.dto.MemberUpdatePasswordRequestDto;
import teamdropin.server.domain.member.dto.MemberUpdateProfileRequestDto;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;
import teamdropin.server.security.utils.CustomAuthorityUtils;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private static final String REASSIGN_EMAIL = "reassign@dropin.com";

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;
    private final CustomAuthorityUtils customAuthorityUtils;

    /**
     * 회원가입
     */
    @Transactional(readOnly = false)
    public Long join(Member member){
        validateDuplicateEmail(member.getUsername());
        validateDuplicateNickname(member.getNickname());
        member.updatePassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 회원조회
     */
    public Member getMember(Long id){
        Optional<Member> optionalMember = memberRepository.findById(id);
        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    /**
     * 이메일 중복체크
     */
    public void validateDuplicateEmail(String email) {
        memberRepository.findByUsername(email)
                .ifPresent(m -> {
                    throw new BusinessLogicException(ExceptionCode.USERNAME_EXISTS);
                });
    }

    /**
     * 유효 아이디 체크
     */
    public Member findVerifyMember(String username){
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    /**
     * 닉네임 중복체크
     */
    public void validateDuplicateNickname(String nickname) {
        memberRepository.findByNickname(nickname)
                .ifPresent(m -> {
                    throw new BusinessLogicException(ExceptionCode.NICKNAME_EXISTS);
                });
    }

    /**
     * 기본 닉네임 생성
     */
    @Transactional(readOnly = false)
    public String createRandomNickname(){
        String[] arr = new String[]{"풀업하는","머슬업하는","월볼샷하는","토투바하는","클린하는","스내치하는","스쿼트하는","로잉타는"};
        String anonymous = "크로스핏터";
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
        Random random = new Random();
        int randomNum = random.nextInt(arr.length);
        return arr[randomNum] + anonymous + uuid;
    }

    /**
     * 회원 탈퇴
     */
    @Transactional(readOnly = false)
    public void deleteMember(String username){
        Member findMember = findVerifyMember(username);

        List<Post> posts = findMember.getPosts();
        List<Comment> comments = findMember.getComments();

        for(Post post : posts){
            post.addMember(memberRepository.findByUsername(REASSIGN_EMAIL).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
            ));
        }

        for(Comment comment : comments){
            comment.addMember(memberRepository.findByUsername(REASSIGN_EMAIL).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
            ));
        }

        memberRepository.delete(findMember);
    }

    /**
     * 회원정보 수정
     */
    @Transactional(readOnly = false)
    public void updateProfile(String username, MemberUpdateProfileRequestDto memberUpdateProfileRequestDto, MultipartFile image) throws IOException {
        Member member = findVerifyMember(username);
        String profileImageUrl = null;
        if(image == null && member.getProfileImageUrl() != null){
            s3Uploader.deleteFile(member.getProfileImageUrl());
        }
        if(image != null){
            profileImageUrl = uploadProfileImageToS3(member, image);
        }
        member.updateInfo(memberUpdateProfileRequestDto.getNickname(), profileImageUrl);
    }

    @Transactional(readOnly = false)
    public void updatePassword(String username, @Valid MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto){
        Member member = findVerifyMember(username);
        if(!passwordEncoder.matches(memberUpdatePasswordRequestDto.getCurrentPassword(), member.getPassword())){
            log.info("match={}", !passwordEncoder.matches(memberUpdatePasswordRequestDto.getCurrentPassword(), member.getPassword()));
            throw new BusinessLogicException(ExceptionCode.PASSWORD_MISMATCH);
        }
        if(!memberUpdatePasswordRequestDto.getUpdatePassword().equals(memberUpdatePasswordRequestDto.getUpdatePasswordCheck())) {
            throw new BusinessLogicException(ExceptionCode.PASSWORD_MISMATCH);
        }
        member.updatePassword(passwordEncoder.encode(memberUpdatePasswordRequestDto.getUpdatePassword()));
    }

    private String getFileExtension(String fileName){
        int dotIndex = fileName.lastIndexOf(".");
        if(dotIndex > 0 && dotIndex < fileName.length() -1){
            return fileName.substring(dotIndex);
        }
        return "";
    }

    private String uploadProfileImageToS3(Member member, MultipartFile image) throws IOException{
        if(member.getProfileImageUrl() != null) {
            s3Uploader.deleteFile(member.getProfileImageUrl());
        }
        String dirName = "member";
        return s3Uploader.upload(image,dirName);
    }

    /**
     * 테스트용
     */
    @PostConstruct
    public void createReassignMember(){

        Member reassignMember =
                Member.builder()
                        .username(REASSIGN_EMAIL)
                        .password(passwordEncoder.encode("Test135!"))
                        .nickname("탈퇴한회원")
                        .name("탈퇴한회원")
                        .oauthProvider("dropin")
                        .roles(customAuthorityUtils.createManagerRoles())
                        .gender(Gender.NOT_SELECT)
                        .build();
        memberRepository.save(reassignMember);
    }
}
