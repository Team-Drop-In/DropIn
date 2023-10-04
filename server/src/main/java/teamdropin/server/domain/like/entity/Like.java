package teamdropin.server.domain.like.entity;

import lombok.*;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.member.entity.Member;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.review.entity.Review;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "heart")
public class Like {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_like_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = LikeCategory.class)
    private LikeCategory likeCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;



    public Like(Member member, Post post, LikeCategory likeCategory){
        this.member = member;
        this.post = post;
        this.likeCategory = likeCategory;
    }

    public Like(Member member, Comment comment, LikeCategory likeCategory){
        this.member = member;
        this.comment = comment;
        this.likeCategory = likeCategory;
    }

    public Like(Member member, Box box, LikeCategory likeCategory){
        this.member = member;
        this.box = box;
        this.likeCategory = likeCategory;
    }

    public Like(Member member, Review review, LikeCategory likeCategory) {
        this.member = member;
        this.review = review;
        this.likeCategory = likeCategory;
    }
}
