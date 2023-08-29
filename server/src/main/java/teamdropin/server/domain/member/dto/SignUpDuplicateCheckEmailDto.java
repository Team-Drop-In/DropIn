package teamdropin.server.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpDuplicateCheckEmailDto {

    @NotBlank
    private String username;
}
