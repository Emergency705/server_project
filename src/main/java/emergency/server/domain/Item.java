package emergency.server.domain;

import emergency.server.domain.enums.PlaceType;
import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;
    private LocalDate closedDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "item")
    private List<Funding> fundings;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews;

}
