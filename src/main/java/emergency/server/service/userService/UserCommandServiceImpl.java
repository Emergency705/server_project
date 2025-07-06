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

import java.time.LocalDate;
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
        validateLoginIdFormat(loginId);
        return userRepository.existsByLoginId(loginId);
    }

    @Override
    @Transactional
    public User joinUser(UserRequestDto.JoinDto request) {
        try {
            validateRequiredFields(request);

            if (userRepository.existsByLoginId(request.getLoginId())) {
                throw new ErrorHandler(ErrorStatus.DUPLICATE_LOGIN_ID);
            }

            validateBirthDate(request.getBirth());

            if (StringUtils.hasText(request.getProfileImage())) {
                if (!isValidBase64Image(request.getProfileImage())) {
                    throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
                }
            }

            User newUser = UserConvertor.toUser(request);
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            newUser.encodePassword(encodedPassword);

            return userRepository.save(newUser);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto.LoginResultDTO loginMember(UserRequestDto.LoginRequestDTO request) {
        try {
            if (!StringUtils.hasText(request.getLoginId()) || !StringUtils.hasText(request.getPassword())) {
                throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
            }

            User user = userRepository.findByLoginId(request.getLoginId())
                    .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new ErrorHandler(ErrorStatus.INVALID_PASSWORD);
            }

            String accessToken = jwtUtil.generateToken(user.getLoginId(), "ROLE_USER");

            return UserConvertor.toLoginResultDTO(user.getUserId(), accessToken);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    //필수 항목 누락 검증
    private void validateRequiredFields(UserRequestDto.JoinDto request) {
        if (!StringUtils.hasText(request.getName())) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (!StringUtils.hasText(request.getLoginId())) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (!StringUtils.hasText(request.getPassword())) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (request.getBirth() == null) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (request.getDisableType() == null) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (request.getHardness() == null) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (request.getDisable() == null) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
        if (request.getRegion() == null) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }
    }

    //로그인 형식 검증
    private void validateLoginIdFormat(String loginId) {
        if (!StringUtils.hasText(loginId)) {
            throw new ErrorHandler(ErrorStatus.MISSING_REQUIRED_FIELDS);
        }

        if (loginId.length() < 4 || loginId.length() > 20) {
            throw new ErrorHandler(ErrorStatus.INVALID_LOGIN_ID_FORMAT);
        }

        if (!loginId.matches("^[a-zA-Z0-9]+$")) {
            throw new ErrorHandler(ErrorStatus.INVALID_LOGIN_ID_FORMAT);
        }
    }

    //생일 일자 검증
    private void validateBirthDate(LocalDate birth) {
        if (birth == null) {
            throw new ErrorHandler(ErrorStatus.INVALID_BIRTH_DATE);
        }

        LocalDate now = LocalDate.now();

        if (birth.isAfter(now)) {
            throw new ErrorHandler(ErrorStatus.FUTURE_BIRTH_DATE);
        }
    }

    //사진 형식 검증
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
                throw new ErrorHandler(ErrorStatus.IMAGE_TOO_LARGE);
            }

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
        }
    }
}