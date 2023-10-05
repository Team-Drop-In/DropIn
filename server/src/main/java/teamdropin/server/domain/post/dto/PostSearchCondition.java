package teamdropin.server.domain.post.dto;

import lombok.Data;

@Data
public class PostSearchCondition {
    private String sortCondition;
    private String searchKeyword;
    private String searchType;
}
