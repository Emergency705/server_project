package emergency.server.controller;

import emergency.server.dto.ReviewDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 기대평 등록
    @PostMapping("/")
    @Operation(summary = "기대평 등록(create review)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<?> saveReview(@Valid ReviewDto.SaveRequest dto, @AuthenticationPrincipal UserDetails user) {
        reviewService.saveReview(dto, user);
        return ApiResponse.onSuccess("review save successful");
    }
}