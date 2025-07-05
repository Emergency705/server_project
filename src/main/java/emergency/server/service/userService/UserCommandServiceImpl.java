package emergency.server.service.userService;

import emergency.server.converter.UserConvertor;
import emergency.server.domain.Region;
import emergency.server.domain.User;
import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;
import emergency.server.global.common.apiPayload.code.status.ErrorStatus;
import emergency.server.global.common.apiPayload.exception.handler.ErrorHandler;
import emergency.server.repository.RegionRepository;
import emergency.server.validation.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import emergency.server.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public User joinUser(UserRequestDto.JoinDto request) {
        Region region = regionRepository.findById(request.getRegionId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 지역입니다."));

        User newUser = UserConvertor.toUser(request, region);

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        newUser.encodePassword(encodedPassword);

        return userRepository.save(newUser);
    }

    public UserResponseDto.LoginResultDTO loginMember(UserRequestDto.LoginRequestDTO request) {
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ErrorHandler(ErrorStatus.INVALID_PASSWORD);
        }

        String accessToken = jwtUtil.generateToken(user.getLoginId(), "ROLE_USER");

        return UserConvertor.toLoginResultDTO(
                user.getUserId(),
                accessToken
        );
    }
}