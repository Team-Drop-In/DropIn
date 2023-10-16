package teamdropin.server.domain.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.global.audit.BaseEntity;
import teamdropin.server.global.audit.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Size(min= 1, max = 50)
    @NotNull
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder.Default
    @OneToMany(mappedBy = "comment"
//            , cascade = CascadeType.REMOVE, orphanRemoval = true
    )
    private List<Like> commentLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
        member.getComments().add(this);
    }

    public void addPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }

    public void updateCommentInfo(String body){
        this.body = body;
    }
}
