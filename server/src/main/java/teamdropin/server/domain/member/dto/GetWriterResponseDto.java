package teamdropin.server.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
public class GetWriterResponseDto {
    private Long id;
    private String nickname;

    public GetWriterResponseDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
