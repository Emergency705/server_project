package emergency.server.controller;

import emergency.server.domain.enums.FacilityType;
import emergency.server.dto.FacilityDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.FacilityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping("/")
    @Operation(summary = "상담 가능 시설 목록 조회 API")
    public ApiResponse<List<FacilityDto.FacilityListDto>> getFacilities(
            @RequestParam(required = false) Boolean isOpen,
            @RequestParam(required = false) FacilityType type
            ) {

        List<FacilityDto.FacilityListDto> facilityList = facilityService.getFacilityList(isOpen, type);

        return ApiResponse.onSuccess(facilityList);
    }

}
