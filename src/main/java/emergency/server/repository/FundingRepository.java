package emergency.server.repository;

import emergency.server.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    // Additional query methods can be defined here if needed
}
