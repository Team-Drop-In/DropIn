package teamdropin.server.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignUpCheckNicknameDto {

    @NotBlank
    private String nickname;
}
