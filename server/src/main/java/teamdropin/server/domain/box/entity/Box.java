package teamdropin.server.domain.box.entity;

import lombok.Getter;
import teamdropin.server.domain.like.boxLike.entity.BoxLike;
import teamdropin.server.global.audit.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Box extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;
    private String boxName;
    private String boxLocation;

    @OneToMany(mappedBy = "box")
    private List<BoxLike> boxLikes = new ArrayList<>();
}
