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
    @Column(nullable = false, length = 50)
    private String name;
    @Column(length = 200)
    private String description;
    @Lob
    private String image;
    @Column(nullable = false)
    private Integer startPrice;
    @Column(nullable = false)
    private Integer maxPrice;
    @Column(nullable = false)
    private Integer maxCount;
    @Enumerated(EnumType.STRING)
    private PlaceType placeType;
    @Column(nullable = false)
    private LocalDate closedDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "item")
    private List<Funding> fundings;

    @OneToMany(mappedBy = "item")
    private List<Review> reviews;

}
