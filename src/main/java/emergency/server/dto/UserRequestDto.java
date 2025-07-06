package emergency.server.dto;

import emergency.server.domain.enums.Disable;
import emergency.server.domain.enums.DisableType;
import emergency.server.domain.enums.HardNess;
import emergency.server.domain.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class UserRequestDto {

    @Getter
    @Setter
    public static class JoinDto {
        @NotBlank(message = "이름은 필수입니다.")
        @Size(min = 2, max = 20, message = "이름은 2-20자 사이여야 합니다.")
        String name;

        @NotBlank(message = "로그인 ID는 필수입니다.")
        @Size(min = 4, max = 20, message = "로그인 ID는 4-20자 사이여야 합니다.")
        String loginId;

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8-20자 사이여야 합니다.")
        String password;

        @NotNull(message = "생년월일은 필수입니다.")
        @Past(message = "생년월일은 현재 날짜보다 이전이어야 합니다.")
        LocalDate birth;

        @NotNull(message = "장애 유형은 필수입니다.")
        DisableType disableType;

        @NotNull(message = "장애 정도는 필수입니다.")
        HardNess hardness;

        @NotNull(message = "장애 여부는 필수입니다.")
        Disable disable;

        @NotNull(message = "지역은 필수입니다.")
        Region region;

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
        @Size(min = 2, max = 20, message = "이름은 2-20자 사이여야 합니다.")
        String name;

        @Past(message = "생년월일은 현재 날짜보다 이전이어야 합니다.")
        LocalDate birth;

        DisableType disableType;
        HardNess hardness;
        Disable disable;
        Region region;
        String profileImage;
    }

    @Getter
    @Setter
    public static class UpdateProfileImageDto {
        String profileImage;
    }
}