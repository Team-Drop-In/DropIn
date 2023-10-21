package teamdropin.server.domain.record.entity;

import lombok.*;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.like.entity.Like;
import teamdropin.server.domain.member.entity.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Record {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_id")
    private Long id;

    private String body;

    private RecordCategory recordCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Box box;

    @Builder.Default
    @OneToMany(mappedBy = "record")
    private List<Like> likes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "record")
    private List<RecordTag> recordTagList = new ArrayList<>();

}
