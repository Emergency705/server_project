package emergency.server.controller;

import emergency.server.global.common.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/") // 마이페이지 조회
    public ApiResponse<?> user() {
        return null;
    }

    @PatchMapping("/")
    public ApiResponse<?> editUserInfo() {
        return null;
    }

}
