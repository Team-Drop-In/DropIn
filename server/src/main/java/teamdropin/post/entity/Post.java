package teamdropin.post.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Post {

    @Id @GeneratedValue
    private Long postId;

    private String title;

    private String body;

    private Long postView;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

}
