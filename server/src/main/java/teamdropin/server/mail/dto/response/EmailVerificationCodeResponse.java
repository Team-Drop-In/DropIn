package teamdropin.server.mail.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationCodeResponse extends EmailResponse {
    private String verificationCode;
}
