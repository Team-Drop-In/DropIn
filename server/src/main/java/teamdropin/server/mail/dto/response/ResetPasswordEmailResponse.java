package teamdropin.server.mail.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordEmailResponse extends EmailResponse {
    private String resetPassword;
}
