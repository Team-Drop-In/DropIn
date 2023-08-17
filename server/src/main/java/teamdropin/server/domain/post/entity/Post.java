package teamdropin.server.domain.post.entity;

import lombok.Getter;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.audit.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String title;
    private String body;
    private Long postView;
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<PostLike> postLikes = new ArrayList<>();

}
