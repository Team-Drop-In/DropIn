package teamdropin.server.domain.post.mapper;

import org.springframework.stereotype.Component;
import teamdropin.server.domain.post.dto.GetPostResponseDto;
import teamdropin.server.domain.post.dto.UpdatePostRequestDto;
import teamdropin.server.domain.post.entity.Post;

@Component
public class PostMapper {

    public GetPostResponseDto postToGetPostResponseDto(Post post){
        return GetPostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .viewCount(post.getViewCount())
                .category(post.getCategory())
                .nickname(post.getMember().getNickname())
                .comments(post.getComments())
                .likeCount(post.getLikeCount())
                .checkLike(false)
                .build();
    }
}
