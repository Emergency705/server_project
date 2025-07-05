package emergency.server.domain;

import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String name;
    private String loginId;
    private String password;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private DisableType disableType;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;
}
