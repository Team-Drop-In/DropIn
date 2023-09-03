package teamdropin.server.domain.post.dto;

import lombok.Data;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Category;
import teamdropin.server.domain.post.entity.Post;

@Data
public class CreatePostRequest {

    private String title;
    private String body;
    private Category category;
    private Member member;


    public Post toEntity(CreatePostRequest createPostRequest){
        return Post.builder()
                .title(createPostRequest.getTitle())
                .body(createPostRequest.getBody())
                .category(createPostRequest.category)
                .build();
    }
}
