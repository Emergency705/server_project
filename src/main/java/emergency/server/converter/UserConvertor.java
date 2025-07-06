package emergency.server.converter;

import emergency.server.domain.User;
import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;

import java.time.LocalDateTime;

public class UserConvertor {

    public static UserResponseDto.JoinResultDto toJoinResultDTO(User user){
        return UserResponseDto.JoinResultDto.builder()
                .userId(user.getUserId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static User toUser(UserRequestDto.JoinDto request) {
        return User.builder()
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .birth(request.getBirth())
                .disableType(request.getDisableType())
                .hardness(request.getHardness())
                .disable(request.getDisable())
                .region(request.getRegion())
                .profileImage(request.getProfileImage())
                .build();
    }

    public static UserResponseDto.LoginResultDTO toLoginResultDTO(Long userId, String accessToken) {
        return UserResponseDto.LoginResultDTO.builder()
                .userId(userId)
                .accessToken(accessToken)
                .build();
    }

    public static UserResponseDto.UserInfoDto toUserInfoDto(User user) {
        return UserResponseDto.UserInfoDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .loginId(user.getLoginId())
                .birth(user.getBirth())
                .disableTypeName(user.getDisableType().getName())
                .hardnessName(user.getHardness().getName())
                .disableName(user.getDisable().getName())
                .regionName(user.getRegion().getKoreanName())
                .profileImage(user.getProfileImage())
                .build();
    }
}