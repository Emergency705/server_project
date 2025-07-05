package emergency.server.repository;

import emergency.server.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    // Additional query methods can be defined here if needed
}
