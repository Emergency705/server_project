package emergency.server.domain;

import emergency.server.domain.enums.DisableType;
import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String loginId;
    private String password;
    private LocalDate birth;
    @Enumerated(EnumType.STRING)
    private DisableType disableType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    public void encodePassword(String password) {
        this.password = password;
    }
}
