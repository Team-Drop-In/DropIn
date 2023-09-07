package teamdropin.server.domain.like.commentLike.entity;

import lombok.Getter;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
public class CommentLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Comment comment;
}
