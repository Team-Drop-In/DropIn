package teamdropin.server.domain.like.boxLike.entity;

import lombok.Getter;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
public class BoxLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "box_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;
}
