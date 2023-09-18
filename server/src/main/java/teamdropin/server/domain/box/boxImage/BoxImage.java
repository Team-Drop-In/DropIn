package teamdropin.server.domain.box.boxImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boxImage_id")
    private Long id;

    private int imageIdx;
    private String boxImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    public BoxImage(String boxImageUrl, int imageIdx, Box box){
        this.boxImageUrl = boxImageUrl;
        this.imageIdx = imageIdx;
        this.box = box;
    }

    public void updateBoxImage(String boxImageUrl, int imageIdx){
        this.boxImageUrl = boxImageUrl;
        this.imageIdx = imageIdx;
    }
}
