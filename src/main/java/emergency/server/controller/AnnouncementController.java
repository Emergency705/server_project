package emergency.server.controller;

import emergency.server.global.common.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    @GetMapping ("/")// 모집공고 리스트 조회
    public ApiResponse<?> announcementList(@RequestParam String announcementType) {
        return null;
    }

    @GetMapping("/{announcementId}")
    public ApiResponse<?> announcement(@PathVariable String announcementId) {
        return null;
    }

}
