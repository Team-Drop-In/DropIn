package teamdropin.server.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Category;
import teamdropin.server.domain.post.entity.Post;

import java.util.List;

@Data
@Builder
public class GetPostResponseDto {

    private Long id;
    private String title;
    private String body;
    private int viewCount;
    private Category category;
    private String nickname;
    private List<Comment> comments;
    private int likeCount;
    private boolean checkLike;

}
