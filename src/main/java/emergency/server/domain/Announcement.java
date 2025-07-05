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
    private String title;

    private String content;

    private String institution;

    private LocalDate openDate;

    private LocalDate closeDate;

    private Boolean isRecruiting;

    @Enumerated(EnumType.STRING)
    private Target target;

    @Enumerated(EnumType.STRING)
    private AnnouncementType type;

}
