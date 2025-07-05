package emergency.server.service.userService;

import emergency.server.domain.User;
import emergency.server.dtos.UserRequestDto;
import emergency.server.dtos.UserResponseDto;

public interface UserCommandService {
     User joinUser(UserRequestDto.JoinDto request);
     UserResponseDto.LoginResultDTO loginMember(UserRequestDto.LoginRequestDTO request);
}
