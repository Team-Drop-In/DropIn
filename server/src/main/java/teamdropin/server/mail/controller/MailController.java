package teamdropin.server.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "7. Mail API", description = "Mail API Document")
@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @Operation(summary = "이메일 인증 메일 발송 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 이메일 발송 성공")
    })
    @PostMapping("/send-verification")
    public ResponseEntity<Void> sendEmailVerification(@RequestBody EmailVerificationCodeRequest request){
        EmailVerificationCodeResponse createdEmail = mailService.createVerificationCodeEmail(request);
        mailService.sendEmail(createdEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "이메일 인증 Code 확인 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 이메일 발송 성공"),
            @ApiResponse(responseCode = "403", description = "인증 코드가 일치하지 않는 경우")
    })
    @PostMapping("verify-code")
    public ResponseEntity<Void> verifyEmail(@RequestBody VerificationCodeRequest verificationCodeRequest){
        mailService.verifyEmailCode(verificationCodeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "임시 비밀번호 메일 발송 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 이메일 발송 성공"),
            @ApiResponse(responseCode = "400", description = "구글 로그인 회원인 경우"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 이메일인 경우")
    })
    @PostMapping("send-new-password")
    public ResponseEntity<Void> sendNewPasswordEmail(@RequestBody FindPasswordRequest findPasswordRequest){
        ResetPasswordEmailResponse resetPasswordEmail = mailService.createResetPasswordEmail(findPasswordRequest);
        mailService.sendEmail(resetPasswordEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
