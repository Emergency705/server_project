package emergency.server.dtos;

import emergency.server.domain.Region;
import emergency.server.domain.enums.DisableType;
import jakarta.persistence.*;
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
}
