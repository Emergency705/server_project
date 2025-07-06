package emergency.server.domain;

import emergency.server.domain.enums.FacilityType;
import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Facility")
public class Facility extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 25)
    private String name;

    @Column(nullable = false)
    private Boolean isOpen;

    @Enumerated(EnumType.STRING)
    private FacilityType type;

    @Column(nullable = false)
    private LocalTime weekdayStartTime;
    @Column(nullable = false)
    private LocalTime weekdayEndTime;
    @Column(nullable = false)
    private LocalTime weekendStartTime;
    @Column(nullable = false)
    private LocalTime weekendEndTime;

    @Column(nullable = false, length = 12)
    private String phone;

    @Column(nullable = false, length = 200)
    private String website;


}
