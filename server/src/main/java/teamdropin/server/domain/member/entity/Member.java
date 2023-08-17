package teamdropin.server.domain.member.entity;

import lombok.Getter;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.global.audit.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private Gender gender;
    private String oauthProvider;
    private Role role;
    private String profileImageUrl;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<PostLike> postLikes = new ArrayList<>();

}
