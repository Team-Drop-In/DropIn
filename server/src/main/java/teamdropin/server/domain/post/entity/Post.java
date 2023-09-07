package teamdropin.server.domain.post.entity;

import lombok.*;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.global.audit.BaseTimeEntity;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.*;
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

    @Size(min = 3 , max = 50)
    private String title;

    @Size(max = 500)
    private String body;

    @Column(columnDefinition = "integer default 0")
    private int viewCount;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Category.class)
    private Category category;

    @Column(columnDefinition = "integer default 0")
    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
//    @OneToMany(mappedBy = "post")
//    private List<PostLike> postLikes = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
    }

    public void viewCountUp(){
        this.viewCount = this.viewCount + 1 ;
    }

    public void getlikeCount(int count){
        this.likeCount = count;
    }

    public void updatePostInfo(String title, String body, Category category){
        this.title = title;
        this.body = body;
        this.category = category;
    }
}
