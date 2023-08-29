package teamdropin.server.mail.dto.request;

import lombok.Data;

@Data
public class FindPasswordRequest{
    private String username;
    private String name;
}
