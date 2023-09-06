package teamdropin.server.domain.post.dto;

import lombok.Data;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Category;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Data
public class CreatePostRequest {

    @Size(min = 3 , max = 50)
    private String title;

    @Size(max = 500)
    private String body;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Category.class)
    private Category category;


    public Post toEntity(CreatePostRequest createPostRequest){
        return Post.builder()
                .title(createPostRequest.getTitle())
                .body(createPostRequest.getBody())
                .category(createPostRequest.category)
                .build();
    }
}
