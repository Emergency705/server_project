package emergency.server.service.userService;

import emergency.server.convertor.UserConvertor;
import emergency.server.dtos.UserResponseDto;
import emergency.server.global.common.apiPayload.code.status.ErrorStatus;
import emergency.server.global.common.apiPayload.exception.handler.ErrorHandler;
import emergency.server.repository.UserRepository;
import emergency.server.validation.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Override
    @Transactional(readOnly = true)
    public UserResponseDto.UserInfoDto getCurrentUser(HttpServletRequest request) {
        Authentication authentication = jwtUtil.extractAuthentication(request);
        String loginId = authentication.getName();

        return userRepository.findByLoginId(loginId)
                .map(UserConvertor::toUserInfoDto)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }
}
