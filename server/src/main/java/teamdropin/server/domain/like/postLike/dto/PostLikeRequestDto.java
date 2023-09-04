package teamdropin.server.domain.like.postLike.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLikeRequestDto {

    private Long memberId;
    private Long postId;

}
