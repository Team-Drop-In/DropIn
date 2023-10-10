package teamdropin.server.domain.member.dto;

import lombok.Data;
import teamdropin.server.domain.member.utils.MemberValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MemberUpdateProfileRequestDto {
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = MemberValidation.NICKNAME_REGEX)
    private String nickname;
    private String profileImageUrl;

}
