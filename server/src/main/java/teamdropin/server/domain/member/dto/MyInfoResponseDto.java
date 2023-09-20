package teamdropin.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.box.dto.LikeBoxResponseDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.member.entity.Gender;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MyInfoResponseDto {
    private String username;
    private String name;
    private String nickname;
    private Gender gender;
    private String oauthProvider;
    private String profileImageUrl;
    private long writePostCount;
    private long writeCommentCount;
    private List<LikeBoxResponseDto> likeBoxList;
}
