package emergency.server.convertor;

import emergency.server.domain.Region;
import emergency.server.domain.User;
import emergency.server.dtos.UserRequestDto;
import emergency.server.dtos.UserResponseDto;

import java.time.LocalDateTime;

public class UserConvertor {

    public static UserResponseDto.JoinResultDto toJoinResultDTO(User user){
        return UserResponseDto.JoinResultDto.builder()
                .userId(user.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static User toUser(UserRequestDto.JoinDto request, Region region) {
        return User.builder()
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .birth(request.getBirth())
                .disableType(request.getDisableType())
                .region(region)
                .build();
    }
}
