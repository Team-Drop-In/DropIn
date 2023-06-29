package teamdropin.comment.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue
    private Long commentId;

    private String body;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
