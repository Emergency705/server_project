package emergency.server.controller;

import emergency.server.convertor.UserConvertor;
import emergency.server.domain.User;
import emergency.server.dtos.UserRequestDto;
import emergency.server.dtos.UserResponseDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.userService.UserCommandService;
import emergency.server.service.userService.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입 API", description = "회원 가입을 진행합니다.")
    public ApiResponse<UserResponseDto.JoinResultDto> join(@RequestBody @Valid UserRequestDto.JoinDto request){
        User user = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConvertor.toJoinResultDTO(user));
    }

    @GetMapping("/info")
    @Operation(summary = "내 정보 조회 API ",
            description = "내 정보를 조회합니다",
            security = { @SecurityRequirement(name = "JWT TOKEN") })
    public ApiResponse<UserResponseDto.UserInfoDto> getMyInfo(HttpServletRequest request) {
        return ApiResponse.onSuccess(userQueryService.getMyUser(request));
    }

    @PatchMapping("/")
    @Operation(summary = "회원 정보 수정 API - 인증 필요",
            description = "현재 로그인한 사용자의 정보를 수정합니다.",
            security = { @SecurityRequirement(name = "JWT TOKEN") })
    public ApiResponse<UserResponseDto.UserInfoDto> updateUserInfo(
            HttpServletRequest request,
            @RequestBody @Valid UserRequestDto.UpdateUserDto updateRequest) {

        UserResponseDto.UserInfoDto result = userQueryService.updateUser(request, updateRequest);
        return ApiResponse.onSuccess(result);
    }
}
