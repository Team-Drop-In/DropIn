package teamdropin.server.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.post.entity.Category;

import java.time.LocalDateTime;

@Data
@Builder

public class PostSearchDto {

    private Long id;
    private String title;
    private String body;
    private int viewCount;
    private Category category;
    private int likeCount;
    private String nickname;
    private int commentCount;
    private LocalDateTime createdDate;


    @QueryProjection
    public PostSearchDto(Long id, String title, String body, int viewCount, Category category, int likeCount, String nickname, int commentCount, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.viewCount = viewCount;
        this.category = category;
        this.likeCount = likeCount;
        this.nickname = nickname;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }
}
