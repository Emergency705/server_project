package emergency.server.service.userService;

import emergency.server.dtos.UserRequestDto;
import emergency.server.dtos.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserQueryService {
    UserResponseDto.UserInfoDto getMyUser(HttpServletRequest request);
    UserResponseDto.UserInfoDto updateUser(HttpServletRequest request, UserRequestDto.UpdateUserDto updateRequest);
}
