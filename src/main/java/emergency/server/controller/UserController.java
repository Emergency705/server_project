package emergency.server.controller;

import emergency.server.convertor.UserConvertor;
import emergency.server.domain.User;
import emergency.server.dtos.UserRequestDto;
import emergency.server.dtos.UserResponseDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.userService.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserCommandService userCommandService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입 API", description = "회원 가입을 진행합니다.")
    public ApiResponse<UserResponseDto.JoinResultDto> join(@RequestBody @Valid UserRequestDto.JoinDto request){
        User user = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConvertor.toJoinResultDTO(user));
    }

    @GetMapping("/") // 마이페이지 조회
    public ApiResponse<?> user() {
        return null;
    }

    @PatchMapping("/")
    public ApiResponse<?> editUserInfo() {
        return null;
    }

}
