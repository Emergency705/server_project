package emergency.server.controller;

import emergency.server.domain.enums.AnnouncementType;
import emergency.server.domain.enums.Region;
import emergency.server.domain.enums.Target;
import emergency.server.dto.AnnouncementResponseDTO;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping ("/")// 모집공고 리스트 조회
    @Operation(summary = "홈화면 공고 리스트 조회 API", description = "type은 필수, 그 외의 사항들은 필수값이 아닙니다")
    public ApiResponse<List<AnnouncementResponseDTO.AnnouncementListDto>> announcementList(
            @RequestParam AnnouncementType type,
            @RequestParam(required = false) Boolean isRecruiting,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) Target target) {
        List<AnnouncementResponseDTO.AnnouncementListDto> announcementList
                = announcementService.getAnnouncementList(type, isRecruiting, region, target);
        return ApiResponse.onSuccess(announcementList);
    }

    @GetMapping("/{announcementId}")
    @Operation(summary = "공고 상세조회 API", description = "공고 상세조회 API입니다")
    public ApiResponse<AnnouncementResponseDTO.AnnouncementDetailsDto> announcement(@PathVariable Long announcementId) {
        AnnouncementResponseDTO.AnnouncementDetailsDto announcementDetails = announcementService.getAnnouncementDetails(announcementId);
        return ApiResponse.onSuccess(announcementDetails);
    }

}
