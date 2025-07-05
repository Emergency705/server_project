package emergency.server.controller;

import emergency.server.domain.enums.AnnouncementType;
import emergency.server.domain.enums.Target;
import emergency.server.dto.AnnouncementResponseDTO;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping ("/")// 모집공고 리스트 조회
    public ApiResponse<List<AnnouncementResponseDTO.AnnouncementListDto>> announcementList(
            @RequestParam AnnouncementType type,
            @RequestParam Boolean isRecruiting,
            @RequestParam Long regionId,
            @RequestParam(required = false) Target target) {
        List<AnnouncementResponseDTO.AnnouncementListDto> announcementList
                = announcementService.getAnnouncementList(type, isRecruiting, regionId, target);
        return ApiResponse.onSuccess(announcementList);
    }

    @GetMapping("/{announcementId}")
    public ApiResponse<?> announcement(@PathVariable String announcementId) {
        return null;
    }

}
