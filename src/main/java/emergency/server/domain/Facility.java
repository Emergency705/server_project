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

    private String name;

    private Boolean isOpen;

    @Enumerated(EnumType.STRING)
    private FacilityType type;

    private LocalTime weekdayStartTime;
    private LocalTime weekdayEndTime;
    private LocalTime weekendStartTime;
    private LocalTime weekendEndTime;

    private String phone;

    private String website;


}
