package emergency.server.service.userService;

import emergency.server.domain.User;
import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;

public interface UserCommandService {
     User joinUser(UserRequestDto.JoinDto request);
     UserResponseDto.LoginResultDTO loginMember(UserRequestDto.LoginRequestDTO request);
}
