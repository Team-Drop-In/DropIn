package teamdropin.server.domain.box.dto.box;

import lombok.Data;

@Data
public class BoxSearchCondition {
    private String orderBy;
    private String search;
    private String barbellDrop;
}
