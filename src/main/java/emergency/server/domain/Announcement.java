package emergency.server.domain;

import emergency.server.domain.enums.AnnouncementType;
import emergency.server.domain.enums.Region;
import emergency.server.domain.enums.Target;
import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "announcement")
public class Announcement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long announcementId;

    @Enumerated(EnumType.STRING)
    private Region region;

    // TODO
    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false, length = 25)
    private String institution;

    @Column(nullable = false)
    private LocalDate openDate;

    @Column(nullable = false)
    private LocalDate closeDate;

    @Column(nullable = false)
    private Boolean isRecruiting;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Target target;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

}
