package emergency.server.service.userService;

import emergency.server.convertor.UserConvertor;
import emergency.server.domain.Region;
import emergency.server.domain.User;
import emergency.server.domain.enums.DisableType;
import emergency.server.dtos.UserRequestDto;
import emergency.server.dtos.UserResponseDto;
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

import java.util.Arrays;

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

        User updatedUser = userRepository.save(user);
        return UserConvertor.toUserInfoDto(updatedUser);
    }
}