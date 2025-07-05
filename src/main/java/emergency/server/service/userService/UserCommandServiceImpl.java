package emergency.server.service.userService;

import emergency.server.converter.UserConvertor;
import emergency.server.domain.User;
import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;
import emergency.server.global.common.apiPayload.code.status.ErrorStatus;
import emergency.server.global.common.apiPayload.exception.handler.ErrorHandler;
import emergency.server.validation.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import emergency.server.repository.UserRepository;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = true)
    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    @Transactional
    public User joinUser(UserRequestDto.JoinDto request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new ErrorHandler(ErrorStatus.DUPLICATE_LOGIN_ID);
        }

        if (StringUtils.hasText(request.getProfileImage())) {
            if (!isValidBase64Image(request.getProfileImage())) {
                throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
            }
        }

        User newUser = UserConvertor.toUser(request);

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

    private boolean isValidBase64Image(String base64Image) {
        if (!StringUtils.hasText(base64Image)) {
            return true;
        }

        try {
            String base64Data = base64Image;
            if (base64Image.contains(",")) {
                String[] parts = base64Image.split(",");
                if (parts.length != 2) {
                    return false;
                }

                String mimeTypePart = parts[0];
                if (!mimeTypePart.matches("data:image/(jpeg|jpg|png|gif|webp);base64")) {
                    return false;
                }

                base64Data = parts[1];
            }

            Base64.getDecoder().decode(base64Data);

            int maxSizeBytes = 5 * 1024 * 1024; // 5MB
            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            if (decodedBytes.length > maxSizeBytes) {
                return false;
            }

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}