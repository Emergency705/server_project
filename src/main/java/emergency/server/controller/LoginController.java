package emergency.server.controller;

import emergency.server.dto.UserRequestDto;
import emergency.server.dto.UserResponseDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.userService.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserCommandService userCommandService;

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인을 진행합니다.")
    public ApiResponse<UserResponseDto.LoginResultDTO> login(@RequestBody @Valid UserRequestDto.LoginRequestDTO request) {
        UserResponseDto.LoginResultDTO loginResult = userCommandService.loginMember(request);
        return ApiResponse.onSuccess(loginResult);
    }

}
