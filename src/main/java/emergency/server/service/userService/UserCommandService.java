package emergency.server.service.userService;

import emergency.server.domain.User;
import emergency.server.dtos.UserRequestDto;

public interface UserCommandService {
     User joinUser(UserRequestDto.JoinDto request) ;
}
