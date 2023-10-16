package teamdropin.server.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.comment.dto.CommentResponseDto;
import teamdropin.server.domain.post.entity.Category;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostResponseDto {

    private Long id;
    private String title;
    private String body;
    private long viewCount;
    private Category category;
    private String nickname;
    private long likeCount;
    private boolean checkPostLike;
    private boolean checkWriter;
    private LocalDateTime createdDate;
    private String profileImageUrl;
    private List<CommentResponseDto> comments;

    public GetPostResponseDto(Long id, String title, String body, long viewCount, Category category, String nickname, long likeCount, boolean checkPostLike, boolean checkWriter, LocalDateTime createdDate, String profileImageUrl) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.viewCount = viewCount;
        this.category = category;
        this.nickname = nickname;
        this.likeCount = likeCount;
        this.checkPostLike = checkPostLike;
        this.checkWriter = checkWriter;
        this.createdDate = createdDate;
        this.profileImageUrl = profileImageUrl;
    }
}
