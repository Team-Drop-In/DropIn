package teamdropin.server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.member.entity.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateGenderRequestDto {

    private Gender gender;
}
