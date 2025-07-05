package emergency.server.dto;

import emergency.server.domain.enums.Disable;
import emergency.server.domain.enums.DisableType;
import emergency.server.domain.enums.HardNess;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UserRequestDto {

    @Getter
    @Setter
    public static class JoinDto {
        String name;
        String loginId;
        String password;
        LocalDate birth;
        DisableType disableType;
        HardNess hardness;
        Disable disable;
        Long regionId;
        String profileImage;
    }

    @Getter
    @Setter
    public static class LoginRequestDTO{
        @NotBlank(message = "아이디는 필수입니다.")
        private String loginId;
        @NotBlank(message = "패스워드는 필수입니다.")
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateUserDto {
        private String name;
        private LocalDate birth;
        private DisableType disableType;
        private HardNess hardness;
        private Disable disable;
        private Long regionId;
        private String profileImage;
    }

    @Getter
    @Setter
    public static class UpdateProfileImageDto {
        @NotBlank(message = "프로필 이미지는 필수입니다.")
        private String profileImage;
    }
}
