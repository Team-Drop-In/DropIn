package teamdropin.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.domain.member.utils.MemberValidation;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignUpRequestDto {

    @Email
    @NotBlank
    private String username;

    /**
     * password
     * 8~20자 사이 공백 허용X
     * 소문자, 대문자, 숫자, 특수기호 1개이상 포함
     */
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = MemberValidation.PASSWORD_REGEX)
    private String password;

    /**
     * nmae
     * 2~10자 사이 한글 영어
     * 특수문자,공백 허용X
     */
    @NotBlank
    @Size(min = 2, max = 10)
    @Pattern(regexp = MemberValidation.NAME_REGEX)
    private String name;

    /**
     * name
     * 2~20자 한글,영어
     * 특수문자, 공백X
     */
    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = MemberValidation.NICKNAME_REGEX)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Gender.class)
    private Gender gender;
}
