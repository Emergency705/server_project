package emergency.server.service.userService;

import emergency.server.dtos.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface UserQueryService {
    UserResponseDto.UserInfoDto getCurrentUser(HttpServletRequest request);

}
