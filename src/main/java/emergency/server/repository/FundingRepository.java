package emergency.server.repository;

import emergency.server.domain.Funding;
import emergency.server.domain.Item;
import emergency.server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundingRepository extends JpaRepository<Funding, Long> {
    // Additional query methods can be defined here if needed

    Optional<Funding> findByItemAndUser(Item item, User user);
}
