package teamdropin.server.mail.dto.response;

import lombok.Data;

@Data
public abstract class EmailResponse {
    private String username;
    private String title;
    private String message;
    private String verificationCode;
}
