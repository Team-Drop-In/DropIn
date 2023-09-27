package teamdropin.server.domain.box.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamdropin.server.global.audit.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxImage extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boxImage_id")
    private Long id;

    private int imageIndex;
    private String boxImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    public BoxImage(String boxImageUrl, int imageIndex, Box box){
        this.boxImageUrl = boxImageUrl;
        this.imageIndex = imageIndex;
        this.box = box;
    }

    public void updateBoxImage(String boxImageUrl, int imageIndex){
        this.boxImageUrl = boxImageUrl;
        this.imageIndex = imageIndex;
    }
}
