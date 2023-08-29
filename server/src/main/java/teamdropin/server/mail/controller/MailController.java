package teamdropin.server.mail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamdropin.server.mail.dto.request.FindPasswordRequest;
import teamdropin.server.mail.dto.response.EmailVerificationCodeResponse;
import teamdropin.server.mail.dto.request.EmailVerificationCodeRequest;
import teamdropin.server.mail.dto.request.VerificationCodeRequest;
import teamdropin.server.mail.dto.response.ResetPasswordEmailResponse;
import teamdropin.server.mail.service.MailService;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/send-verification")
    public ResponseEntity<Void> sendEmailVerification(@RequestBody EmailVerificationCodeRequest request){
        EmailVerificationCodeResponse createdEmail = mailService.createVerificationCodeEmail(request);
        mailService.sendEmail(createdEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("verify-code")
    public ResponseEntity<Void> verifyEmail(@RequestBody VerificationCodeRequest verificationCodeRequest){
        mailService.verifyEmailCode(verificationCodeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("send-new-password")
    public ResponseEntity<Void> sendNewPasswordEmail(@RequestBody FindPasswordRequest findPasswordRequest){
        ResetPasswordEmailResponse resetPasswordEmail = mailService.createResetPasswordEmail(findPasswordRequest);
        mailService.sendEmail(resetPasswordEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
