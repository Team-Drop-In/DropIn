package teamdropin.server.domain.boxTag.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.entity.Box;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoxTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    private long searchCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    public void addBox(Box box){
        this.box = box;
        box.getBoxTagList().add(this);
    }

    public BoxTag(String tagName){
        this.tagName = tagName;
    }
}
