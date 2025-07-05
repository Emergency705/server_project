package emergency.server.domain;

import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "item")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long itemId;
    private String name;
    private String description;
    private String imageUrl;
    private Integer startPrice;
    private Integer maxPrice;
    private Integer maxCount;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

}
