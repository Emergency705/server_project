package emergency.server.repository;

import emergency.server.domain.Item;
import emergency.server.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i " +
            "join fetch i.fundings f " +
            "where f.user.loginId = :loginId ")
    Set<Item> findByUser(@Param("loginId") String loginId);
}
