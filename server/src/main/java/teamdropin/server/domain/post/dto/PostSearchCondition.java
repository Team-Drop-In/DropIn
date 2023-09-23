package teamdropin.server.domain.post.dto;

import lombok.Data;

@Data
public class PostSearchCondition {
    private String orderBy;
    private String search;
}
