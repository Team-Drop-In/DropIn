package teamdropin.server.domain.like.postLike.entity;

import lombok.*;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public PostLike(Member member, Post post){
        this.member = member;
        this.post = post;
    }
}
