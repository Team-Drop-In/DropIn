package teamdropin.server.domain.post.dto;

import lombok.Builder;
import lombok.Data;
import teamdropin.server.domain.post.entity.Category;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UpdatePostRequestDto {

    @NotBlank
    @Size(min = 3 , max = 50)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String body;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Category.class)
    private Category category;

}
