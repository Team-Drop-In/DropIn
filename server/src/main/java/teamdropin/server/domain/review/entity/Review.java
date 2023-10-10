package teamdropin.server.domain.review.entity;

import lombok.*;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.audit.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String body;

    private String anonymousName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    @Builder.Default
    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> reviewLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
        member.getReviews().add(this);
    }

    public void addBox(Box box){
        this.box = box;
    }

    public void updateReviewInfo(String body) {
        this.body = body;
    }
}

