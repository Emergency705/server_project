package emergency.server.service.userService;

import emergency.server.converter.UserConvertor;
import emergency.server.domain.User;
import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;
import emergency.server.global.common.apiPayload.code.status.ErrorStatus;
import emergency.server.global.common.apiPayload.exception.handler.ErrorHandler;
import emergency.server.repository.UserRepository;
import emergency.server.validation.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto.UserInfoDto getMyUser(HttpServletRequest request) {
        try {
            Authentication authentication = extractAuthentication(request);
            String loginId = authentication.getName();

            return userRepository.findByLoginId(loginId)
                    .map(UserConvertor::toUserInfoDto)
                    .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public UserResponseDto.UserInfoDto updateUser(HttpServletRequest request, UserRequestDto.UpdateUserDto updateRequest) {
        try {
            Authentication authentication = extractAuthentication(request);
            String loginId = authentication.getName();

            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

            if (updateRequest.getName() != null) {
                validateAndUpdateName(user, updateRequest.getName());
            }

            if (updateRequest.getBirth() != null) {
                validateAndUpdateBirth(user, updateRequest.getBirth());
            }

            if (updateRequest.getDisableType() != null) {
                user.setDisableType(updateRequest.getDisableType());
            }

            if (updateRequest.getHardness() != null) {
                user.setHardness(updateRequest.getHardness());
            }

            if (updateRequest.getDisable() != null) {
                user.setDisable(updateRequest.getDisable());
            }

            if (updateRequest.getRegion() != null) {
                user.setRegion(updateRequest.getRegion());
            }

            if (updateRequest.getProfileImage() != null) {
                validateAndUpdateProfileImage(user, updateRequest.getProfileImage());
            }

            User updatedUser = userRepository.save(user);

            return UserConvertor.toUserInfoDto(updatedUser);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public UserResponseDto.UserInfoDto updateProfileImage(HttpServletRequest request, UserRequestDto.UpdateProfileImageDto imageRequest) {
        try {
            Authentication authentication = extractAuthentication(request);
            String loginId = authentication.getName();

            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

            if (StringUtils.hasText(imageRequest.getProfileImage())) {
                if (!isValidBase64Image(imageRequest.getProfileImage())) {
                    throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
                }
                user.setProfileImage(imageRequest.getProfileImage());
            } else {
                user.setProfileImage(null);
            }

            User updatedUser = userRepository.save(user);

            return UserConvertor.toUserInfoDto(updatedUser);
        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public void deleteProfileImage(HttpServletRequest request) {
        try {
            Authentication authentication = extractAuthentication(request);
            String loginId = authentication.getName();

            User user = userRepository.findByLoginId(loginId)
                    .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

            user.setProfileImage(null);
            userRepository.save(user);

        } catch (ErrorHandler e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorHandler(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    private Authentication extractAuthentication(HttpServletRequest request) {
        Authentication authentication = jwtUtil.extractAuthentication(request);
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ErrorHandler(ErrorStatus.UNAUTHORIZED_ACCESS);
        }
        return authentication;
    }

    private void validateAndUpdateName(User user, String name) {
        if (!StringUtils.hasText(name)) {
            throw new ErrorHandler(ErrorStatus.INVALID_NAME_FORMAT);
        }

        String trimmedName = name.trim();
        if (trimmedName.length() < 2 || trimmedName.length() > 20) {
            throw new ErrorHandler(ErrorStatus.INVALID_NAME_FORMAT);
        }

        if (!trimmedName.matches("^[가-힣a-zA-Z\\s]+$")) {
            throw new ErrorHandler(ErrorStatus.INVALID_NAME_FORMAT);
        }

        user.setName(trimmedName);
    }

    //생일 검증
    private void validateAndUpdateBirth(User user, LocalDate birth) {
        LocalDate now = LocalDate.now();

        if (birth.isAfter(now)) {
            throw new ErrorHandler(ErrorStatus.FUTURE_BIRTH_DATE);
        }

        user.setBirth(birth);
    }

    //사진 업데이트 밑 검증
    private void validateAndUpdateProfileImage(User user, String profileImage) {
        if (StringUtils.hasText(profileImage)) {
            if (!isValidBase64Image(profileImage)) {
                throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
            }
            user.setProfileImage(profileImage);
        } else {
            user.setProfileImage(null);
        }
    }

    //사진 검증
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