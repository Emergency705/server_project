package emergency.server.domain;

import emergency.server.domain.enums.DisableType;
import emergency.server.domain.enums.HardNess;
import emergency.server.domain.enums.Disable;
import emergency.server.domain.enums.Region;
import emergency.server.global.data.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
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
    @Column(name = "disable_type")
    private DisableType disableType;

    @Enumerated(EnumType.STRING)
    @Column(name = "hardness", length = 20)
    private HardNess hardness;

    @Enumerated(EnumType.STRING)
    @Column(name = "disable", length = 20)
    private Disable disable;

    @Enumerated(EnumType.STRING)
    @Column(name = "region", length = 20)
    private Region region;

    @Lob
    @Column(name = "profile_image", columnDefinition = "LONGTEXT")
    private String profileImage;

    public void encodePassword(String password) {
        this.password = password;
    }
}