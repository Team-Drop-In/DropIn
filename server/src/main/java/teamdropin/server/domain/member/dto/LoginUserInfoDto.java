package teamdropin.server.domain.member.dto;

import lombok.*;

@Data
@Builder
public class LoginUserInfoDto {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    public LoginUserInfoDto(Long id, String nickname, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }
}
