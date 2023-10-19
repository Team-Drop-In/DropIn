package teamdropin.server.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.member.dto.GetWriterResponseDto;
import teamdropin.server.domain.post.entity.Category;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class PostSearchDto {

    private Long id;
    private String title;
    private String body;
    private long viewCount;
    private Category category;
    private int likeCount;
    private GetWriterResponseDto writer;
    private int commentCount;
    private LocalDateTime createdDate;

    public PostSearchDto(Long id, String title, String body, long viewCount, Category category, int likeCount, GetWriterResponseDto writer, int commentCount, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.viewCount = viewCount;
        this.category = category;
        this.likeCount = likeCount;
        this.writer = writer;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }

    //    @QueryProjection
//    public PostSearchDto(Long id, String title, String body, long viewCount, Category category, int likeCount, GetPostMemberResponseDto writer, int commentCount, LocalDateTime createdDate,String profileImageUrl) {
//        this.id = id;
//        this.title = title;
//        this.body = body;
//        this.viewCount = viewCount;
//        this.category = category;
//        this.likeCount = likeCount;
//        this.writer = writer;
//        this.commentCount = commentCount;
//        this.createdDate = createdDate;
//        this.profileImageUrl = profileImageUrl;
//    }
}
