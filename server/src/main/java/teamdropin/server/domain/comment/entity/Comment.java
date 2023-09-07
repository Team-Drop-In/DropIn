package teamdropin.server.domain.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.like.commentLike.entity.CommentLike;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.global.audit.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
    }

    public void addPost(Post post){
        this.post = post;
    }
}
