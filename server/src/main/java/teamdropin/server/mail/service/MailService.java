package teamdropin.server.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.member.repository.MemberRepository;
import teamdropin.server.domain.member.service.MemberService;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;
import teamdropin.server.mail.dto.request.FindPasswordRequest;
import teamdropin.server.mail.dto.response.EmailResponse;
import teamdropin.server.mail.dto.response.EmailVerificationCodeResponse;
import teamdropin.server.mail.dto.request.EmailVerificationCodeRequest;
import teamdropin.server.mail.dto.request.VerificationCodeRequest;
import teamdropin.server.mail.dto.response.ResetPasswordEmailResponse;
import teamdropin.server.redis.util.RedisUtil;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailService {

    private final MemberRepository memberRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;
    private final MemberService memberService;

    private static final String FROM_ADDRESS = "projectdropinapp@gmail.com";
    private static final String VERIFICATION_EMAIL_TITLE = "DropIn 회원가입 이메일 인증번호 발송 메일 입니다.";
    private static final String RESET_PASSWORD_EMAIL_TITLE = "DropIn 임시 비밀번호 발송 메일 입니다.";


    public void sendEmail(EmailResponse emailResponse){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailResponse.getUsername());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(emailResponse.getTitle());
        message.setText(emailResponse.getMessage());
        javaMailSender.send(message);
    }

    public EmailVerificationCodeResponse createVerificationCodeEmail(EmailVerificationCodeRequest emailVerificationCodeRequest){
        String createdCode = createCode();
        EmailVerificationCodeResponse verificationCodeEmail = new EmailVerificationCodeResponse();
        verificationCodeEmail.setUsername(emailVerificationCodeRequest.getUsername());
        verificationCodeEmail.setTitle(VERIFICATION_EMAIL_TITLE);
        verificationCodeEmail.setMessage("DropIn에 오신것을 환영 합니다! 이메일 인증번호는 " + createdCode + " 입니다.");
        if(redisUtil.existData(emailVerificationCodeRequest.getUsername())){
            redisUtil.deleteData(verificationCodeEmail.getUsername());
        }
        redisUtil.setData(emailVerificationCodeRequest.getUsername(), createdCode, 300);
        return verificationCodeEmail;
    }

    @Transactional(readOnly = false)
    public ResetPasswordEmailResponse createResetPasswordEmail(FindPasswordRequest findPasswordRequest){
        Member member = memberService.findVerifyMember(findPasswordRequest.getUsername());
        if(!findPasswordRequest.getName().equals(member.getName())){
            throw new BusinessLogicException(ExceptionCode.USER_NOT_FOUND);
        }
        String createdPassword = createPassword();
        String encodedPassword = passwordEncoder.encode(createdPassword);
        member.updatePassword(encodedPassword);
        ResetPasswordEmailResponse resetPasswordEmail = new ResetPasswordEmailResponse();
        resetPasswordEmail.setUsername(member.getUsername());
        resetPasswordEmail.setTitle(RESET_PASSWORD_EMAIL_TITLE);
        resetPasswordEmail.setMessage("DropIn 임시 비밀번호는 " + createdPassword + " 입니다.");
        return resetPasswordEmail;
    }



    public void verifyEmailCode(VerificationCodeRequest verificationCodeRequest){
        String findCode = redisUtil.getData(verificationCodeRequest.getUsername());
        if(findCode == null){
            throw new BusinessLogicException(ExceptionCode.CODE_MISMATCH);
        } else if(!findCode.equals(verificationCodeRequest.getVerificationCode())) {
            throw new BusinessLogicException(ExceptionCode.CODE_MISMATCH);
        } else{
            redisUtil.deleteData(verificationCodeRequest.getUsername());
        }
    }

    private String createCode() {
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        StringBuilder createdCode = new StringBuilder();
        int codeLength = 6;
        int idx = 0;
        for(int i = 0; i < codeLength; i++){
            idx = (int) (Math.random() * charSet.length);
            createdCode.append(charSet[idx]);
        }
        return createdCode.toString();
    }

    private String createPassword() {
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '!', '@', '#', '$', '%', '^', '&', '*', '(', ')'
        };
        StringBuilder createdResetPassword = new StringBuilder();
        int passwordLength = 14;
        int idx = 0;
        for(int i  = 0; i < passwordLength; i++){
            idx  = (int)(Math.random() * charSet.length);
            createdResetPassword.append(charSet[idx]);
        }
        return createdResetPassword.toString();
    }

}
