package emergency.server.dtos;

import emergency.server.domain.Region;
import emergency.server.domain.enums.DisableType;
import jakarta.persistence.*;
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
        Long regionId;
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
        private Long regionId;
    }
}
