package emergency.server.repository;

import emergency.server.domain.Announcement;
import emergency.server.domain.enums.AnnouncementType;
import emergency.server.domain.enums.Region;
import emergency.server.domain.enums.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("""
        SELECT a FROM Announcement a
        WHERE (:type IS NULL OR a.type = :type)
          AND (:isRecruiting IS NULL OR a.isRecruiting = :isRecruiting)
          AND (:region IS NULL OR a.region = :region)
          AND (:target IS NULL OR a.target = :target)
    """)
    List<Announcement> findByFilter(
            @Param("type") AnnouncementType type,
            @Param("isRecruiting") Boolean isRecruiting,
            @Param("region") Region region,
            @Param("target") Target target
    );




}
