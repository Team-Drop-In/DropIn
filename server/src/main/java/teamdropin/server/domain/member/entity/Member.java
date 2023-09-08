package teamdropin.server.domain.member.entity;

import lombok.*;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.utils.MemberValidation;
import teamdropin.server.domain.post.entity.Post;
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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    private String profileImageUrl;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    public Member updatePassword(String encodedPassword){
        this.password = encodedPassword;
        return this;
    }

    public Member updateInfo(String nickname, String profileImageUrl){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        return this;
    }
}
