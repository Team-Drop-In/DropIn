package teamdropin.server.domain.box.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.audit.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Box extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_id")
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private String phoneNumber;

    @NotNull
    private Integer cost;

    @NotNull
    private Integer area;

    private int viewCount;

    private boolean barbellDrop;

    private String url;

    private String detail;

    private String mainImageUrl;

    @OneToMany(mappedBy = "box", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoxImage> boxImageList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "box")
    private List<Like> boxLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
    }

    public void viewCountUp(){this.viewCount = this.viewCount +1 ;}

//    public void updateMainImageUrl(String mainImageUrl){
//        this.mainImageUrl = mainImageUrl;
//    }

    public void updateBox(String name, String location, String phoneNumber, int cost, int area, boolean barbellDrop, String url, String detail){
        this.name= name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.cost = cost;
        this.area = area;
        this.barbellDrop = barbellDrop;
        this.url = url;
        this.detail = detail;
    }
}
