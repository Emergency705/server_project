package emergency.server.controller;

import emergency.server.converter.ReviewConverter;
import emergency.server.domain.Review;
import emergency.server.dto.ReviewDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<?> saveReview(@Valid ReviewDto.CreateRequest dto, @AuthenticationPrincipal UserDetails user) {
        reviewService.saveReview(dto, user);
        return ApiResponse.onSuccess("review save successful");
    }

    // 상품별 리뷰 목록 조회
    @GetMapping("/item/{itemId}")
    @Operation(summary = "상품별 리뷰 목록(review List by item)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "itemId", description = "조회하려는 리뷰의 아이디, path variable 입니다!"),
    })
    public ApiResponse<?> getReview(@PathVariable("itemId") Long itemId) {
        List<Review> reviewList = reviewService.getReviewsByItemId(itemId);
        return ApiResponse.onSuccess(ReviewConverter.toListResponse(reviewList));
    }
}