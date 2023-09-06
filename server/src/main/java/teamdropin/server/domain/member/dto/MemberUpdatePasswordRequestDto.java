package teamdropin.server.domain.member.dto;

import lombok.Data;
import teamdropin.server.domain.member.utils.MemberValidation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MemberUpdatePasswordRequestDto {

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = MemberValidation.PASSWORD_REGEX,
            message = "비밀번호는 공백이 없고 적어도 영어 대,소문자 , 숫자, 특수문자 !@#$%^&*() 1개 이상을 포함해야 합니다.")
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = MemberValidation.PASSWORD_REGEX,
            message = "비밀번호는 공백이 없고 적어도 영어 대,소문자 , 숫자, 특수문자 !@#$%^&*() 1개 이상을 포함해야 합니다.")
    private String updatePassword;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = MemberValidation.PASSWORD_REGEX,
            message = "비밀번호는 공백이 없고 적어도 영어 대,소문자 , 숫자, 특수문자 !@#$%^&*() 1개 이상을 포함해야 합니다.")
    private String updatePasswordCheck;
}
