package teamdropin.server.domain.member.entity;

import lombok.*;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.utils.MemberValidation;
import teamdropin.server.domain.post.entity.Post;
import teamdropin.server.domain.review.entity.Review;
import teamdropin.server.global.audit.BaseTimeEntity;
import teamdropin.server.global.util.enumValid.ValidEnum;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Email
    private String username;

    private String password;

    @NotBlank
    @Size(min = 2, max = 10)
    @Pattern(regexp = MemberValidation.NAME_REGEX)
    private String name;

    @NotBlank
    @Size(min = 2, max = 20)
    @Pattern(regexp = MemberValidation.NICKNAME_REGEX)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Gender.class)
    private Gender gender;

    @NotBlank
    private String oauthProvider;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    private String profileImageUrl;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Box> boxes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Like> likes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    public void updatePassword(String encodedPassword){
        this.password = encodedPassword;
    }

    public void updateInfo(String nickname, String profileImageUrl){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }
}
