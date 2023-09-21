package teamdropin.server.domain.box.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

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
    private Integer cost;

    @NotNull
    private Integer area;

    private boolean barbellDrop;
    private String url;
    private String detail;

    @Builder.Default
    private HashMap<Integer, String> imageInfo = new HashMap<>();

}
