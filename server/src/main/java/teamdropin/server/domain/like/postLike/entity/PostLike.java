package teamdropin.server.domain.like.postLike.entity;

import lombok.Getter;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;

import javax.persistence.*;

@Entity
@Getter
public class PostLike {
    @Id @GeneratedValue
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
