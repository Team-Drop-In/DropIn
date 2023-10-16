package teamdropin.server.mail.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCodeRequest {
    private String username;
    private String verificationCode;
}
