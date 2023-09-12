package teamdropin.server.domain.box.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxCreateRequestDto {

    private String name;
    private String location;
    private String phoneNumber;
    private int cost;
    private int area;
    private boolean barbellDrop;
    private String url;
    private String detail;

}
