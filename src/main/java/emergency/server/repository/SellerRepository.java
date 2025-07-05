package emergency.server.repository;

import emergency.server.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    // Additional query methods can be defined here if needed
}
