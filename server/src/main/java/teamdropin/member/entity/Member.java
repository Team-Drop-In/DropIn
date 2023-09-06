package teamdropin.member.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberId;

    @Column
    private String email;

    @Column
    private String name;

    @Column
    private String nickname;

    @Column
    private Gender gender;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
