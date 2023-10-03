package teamdropin.server.domain.post.entity;

import lombok.*;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.audit.BaseTimeEntity;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotBlank
    @Size(min = 3 , max = 50)
    private String title;

    @NotBlank
    @Size(max = 500)
    private String body;

    @Column(columnDefinition = "integer default 0")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Category.class)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> postLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
        member.getPosts().add(this);
    }

    public void viewCountUp(){
        this.viewCount = this.viewCount + 1 ;
    }

    public void updatePostInfo(String title, String body, Category category){
        this.title = title;
        this.body = body;
        this.category = category;
    }
}
