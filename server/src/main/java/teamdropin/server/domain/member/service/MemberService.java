package teamdropin.server.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import teamdropin.server.aws.service.S3Uploader;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.comment.repository.CommentRepository;
import teamdropin.server.domain.like.repository.LikeRepository;
import teamdropin.server.domain.member.dto.MemberUpdateGenderRequestDto;
import teamdropin.server.domain.member.dto.MemberUpdatePasswordRequestDto;
import teamdropin.server.domain.member.dto.MemberUpdateProfileRequestDto;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.post.repository.PostRepository;
import teamdropin.server.domain.review.repository.ReviewRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;
import teamdropin.server.security.utils.CustomAuthorityUtils;

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
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;
    private final BoxRepository boxRepository;
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
        Member reassignMember = memberRepository.findByUsername(REASSIGN_EMAIL).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND)
        );

        int updatePostMemberId = postRepository.updateMemberIdForPosts(findMember.getId(),reassignMember.getId());
        int updateCommentMemberId = commentRepository.updateMemberIdForComments(findMember.getId(),reassignMember.getId());
        int updateReviewMemberId = reviewRepository.updateMemberIdForReviews(findMember.getId(),reassignMember.getId());
        int updateLikeMemberId = likeRepository.updateMemberIdForLikes(findMember.getId(),reassignMember.getId());


        memberRepository.delete(findMember);
    }

    /**
     * 회원정보 수정
     */
    @Transactional(readOnly = false)
    public String updateProfile(String username, MemberUpdateProfileRequestDto memberUpdateProfileRequestDto, MultipartFile image) throws IOException {
        Member member = findVerifyMember(username);

        log.info("member nickname ={}", member.getNickname());
        log.info("request nickname ={}", memberUpdateProfileRequestDto.getNickname());
        log.info("check = {}", member.getNickname().equals(memberUpdateProfileRequestDto.getNickname()));


        if(!memberUpdateProfileRequestDto.getNickname().equals(member.getNickname())) {
            validateDuplicateNickname(memberUpdateProfileRequestDto.getNickname());
        }
        String profileImageUrl = null;
        if(image == null && member.getProfileImageUrl() != null){
            s3Uploader.deleteFile(member.getProfileImageUrl());
        }
        if(image != null){
            profileImageUrl = uploadProfileImageToS3(member, image);
        }
        member.updateInfo(memberUpdateProfileRequestDto.getNickname(), profileImageUrl);

        return profileImageUrl;
    }

    /**
     * 회원 성별 수정(OAuth 로그인 유저만)
     */
    @Transactional(readOnly = false)
    public void updateGender(Member member, MemberUpdateGenderRequestDto memberUpdateGenderRequestDto) {
        Member findMember = findVerifyMember(member.getUsername());
        if(findMember.getGender() == Gender.NOT_SELECT) {
            findMember.updateGender(memberUpdateGenderRequestDto.getGender());
        } else {
            throw new BusinessLogicException(ExceptionCode.USER_GENDER_CHANGE_IMPOSSIBLE);
        }
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

}
