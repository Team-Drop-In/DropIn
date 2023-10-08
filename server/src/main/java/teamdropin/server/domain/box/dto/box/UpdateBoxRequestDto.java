package teamdropin.server.domain.box.dto.box;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.dto.boxTag.UpdateBoxTagRequestDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBoxRequestDto {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String location;
    private String phoneNumber;
    @NotNull
    private Long cost;
    @NotNull
    private Long area;
    private boolean barbellDrop;
    private String url;
    private String detail;
    private List<String> tagList;
    private HashMap<Long,String> imageInfo;

}
