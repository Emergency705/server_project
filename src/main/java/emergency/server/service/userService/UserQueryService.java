package emergency.server.service.userService;

import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserQueryService {
    UserResponseDto.UserInfoDto getMyUser(HttpServletRequest request);
    UserResponseDto.UserInfoDto updateUser(HttpServletRequest request, UserRequestDto.UpdateUserDto updateRequest);
    UserResponseDto.UserInfoDto updateProfileImage(HttpServletRequest request, UserRequestDto.UpdateProfileImageDto imageRequest);
    void deleteProfileImage(HttpServletRequest request);
}
