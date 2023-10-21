package teamdropin.server.domain.record.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecordTag {

    @Id @GeneratedValue
    @Column(name = "recordTag_id")
    private Long id;

    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id")
    private Record record;

    public void addRecord(Record record){
        this.record = record;
        record.getRecordTagList().add(this);
    }
}
