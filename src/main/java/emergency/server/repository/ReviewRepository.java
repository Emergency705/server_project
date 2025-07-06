package emergency.server.repository;

import emergency.server.domain.Item;
import emergency.server.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 리뷰를 아이템 ID로 조회하는 메소드
    @Query("SELECT r FROM Review r " +
            "join fetch r.user u " +
            "WHERE r.item = :item")
    List<Review> findAllByItem(Item item);

}
