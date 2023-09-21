package teamdropin.server.domain.post.dto;

import lombok.Data;

@Data
public class PostSearchCondition {
    private String sortBy;
    private String search;
}
