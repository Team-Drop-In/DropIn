package teamdropin.server.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpDuplicateCheckNicknameDto {

    @NotBlank
    private String nickname;
}
