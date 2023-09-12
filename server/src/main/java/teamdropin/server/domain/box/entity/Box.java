package teamdropin.server.domain.box.entity;

import lombok.Builder;
import lombok.Getter;
import teamdropin.server.domain.box.boxImage.BoxImage;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.audit.BaseEntity;
import teamdropin.server.global.audit.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
public class Box extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;

    private String boxName;

    private String boxLocation;

    @OneToMany(mappedBy = "box")
    private List<BoxImage> boxImageUrlList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "box")
    private List<Like> boxLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
    }
}
