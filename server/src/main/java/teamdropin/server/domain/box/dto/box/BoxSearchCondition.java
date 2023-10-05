package teamdropin.server.domain.box.dto.box;

import lombok.Data;

@Data
public class BoxSearchCondition {
    private String sortCondition;
    private String searchKeyword;
    private String searchType;
    private String barbellDrop;
}
