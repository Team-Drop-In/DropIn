package teamdropin.server.domain.like.dto;

import lombok.Data;
import teamdropin.server.domain.like.entity.LikeCategory;
import teamdropin.server.domain.member.entity.Gender;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class LikeRequestDto {


    private Long likeCategoryId;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Gender.class)
    private LikeCategory likeCategory;

}
