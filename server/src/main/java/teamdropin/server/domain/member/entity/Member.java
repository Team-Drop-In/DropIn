package teamdropin.server.domain.member.entity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamdropin.server.domain.comment.entity.Comment;
import teamdropin.server.domain.like.postLike.entity.PostLike;
import teamdropin.server.domain.member.util.MemberValidation;
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

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Email
    private String email;

    @NotBlank
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

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = OauthProvider.class)
    private OauthProvider oauthProvider;

    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = Role.class)
    private Role role;

    private String profileImageUrl;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<PostLike> postLikes = new ArrayList<>();


    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }
}
