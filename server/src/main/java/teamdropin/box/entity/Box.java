package teamdropin.box.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
public class Box {
    @Id
    @GeneratedValue
    private Long boxId;

    private String name;

    private String location;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
