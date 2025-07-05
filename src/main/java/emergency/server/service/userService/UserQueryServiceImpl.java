package emergency.server.service.userService;

import emergency.server.converter.UserConvertor;
import emergency.server.domain.Region;
import emergency.server.domain.User;
import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;
import emergency.server.global.common.apiPayload.code.status.ErrorStatus;
import emergency.server.global.common.apiPayload.exception.handler.ErrorHandler;
import emergency.server.repository.RegionRepository;
import emergency.server.repository.UserRepository;
import emergency.server.validation.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto.UserInfoDto getMyUser(HttpServletRequest request) {
        Authentication authentication = jwtUtil.extractAuthentication(request);
        String loginId = authentication.getName();

        return userRepository.findByLoginId(loginId)
                .map(UserConvertor::toUserInfoDto)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    @Transactional
    public UserResponseDto.UserInfoDto updateUser(HttpServletRequest request, UserRequestDto.UpdateUserDto updateRequest) {
        Authentication authentication = jwtUtil.extractAuthentication(request);
        String loginId = authentication.getName();

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (updateRequest.getName() != null) {
            if (!StringUtils.hasText(updateRequest.getName())) {
                throw new ErrorHandler(ErrorStatus.INVALID_NAME_FORMAT);
            }

            String trimmedName = updateRequest.getName().trim();
            if (trimmedName.length() < 2 || trimmedName.length() > 20) {
                throw new ErrorHandler(ErrorStatus.INVALID_NAME_FORMAT);
            }

            user.setName(trimmedName);
        }

        if (updateRequest.getBirth() != null) {
            user.setBirth(updateRequest.getBirth());
        }

        if (updateRequest.getDisableType() != null) {
            user.setDisableType(updateRequest.getDisableType());
        }

        if (updateRequest.getRegionId() != null) {
            Region region = regionRepository.findById(updateRequest.getRegionId())
                    .orElseThrow(() -> new ErrorHandler(ErrorStatus.REGION_NOT_FOUND));
            user.setRegion(region);
        }

        if (updateRequest.getProfileImage() != null) {
            if (isValidBase64Image(updateRequest.getProfileImage())) {
                user.setProfileImage(updateRequest.getProfileImage());
            } else {
                throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
            }
        }

        User updatedUser = userRepository.save(user);
        return UserConvertor.toUserInfoDto(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDto.UserInfoDto updateProfileImage(HttpServletRequest request, UserRequestDto.UpdateProfileImageDto imageRequest) {
        Authentication authentication = jwtUtil.extractAuthentication(request);
        String loginId = authentication.getName();

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!isValidBase64Image(imageRequest.getProfileImage())) {
            throw new ErrorHandler(ErrorStatus.INVALID_IMAGE_FORMAT);
        }

        user.setProfileImage(imageRequest.getProfileImage());
        User updatedUser = userRepository.save(user);

        return UserConvertor.toUserInfoDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteProfileImage(HttpServletRequest request) {
        Authentication authentication = jwtUtil.extractAuthentication(request);
        String loginId = authentication.getName();

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.MEMBER_NOT_FOUND));

        user.setProfileImage(null);
        userRepository.save(user);
    }

    private boolean isValidBase64Image(String base64Image) {
        if (!StringUtils.hasText(base64Image)) {
            return false;
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