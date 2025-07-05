package emergency.server.controller;

import emergency.server.domain.Item;
import emergency.server.dto.FundingDto;
import emergency.server.dto.ItemDto;
import emergency.server.global.common.apiPayload.ApiResponse;
import emergency.server.service.FundingService;
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
@RequestMapping("/funding")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @GetMapping("/") // 아이템 목록 조회
    @Operation(summary = "구매 목록 조회(item list)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<?> getItemList() {
        List<ItemDto.ListResponse> dtos = fundingService.getItemList();
        return ApiResponse.onSuccess(dtos);
    }

    @GetMapping("/{itemId}") // 아이템 상세조회
    @Operation(summary = "구매 상세보기(item detail)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "itemId", description = "조회하려는 구매항목의 ID, path variable 입니다!"),
    })
    public ApiResponse<?> itemDetails(@PathVariable("itemId") Long itemId) {
        ItemDto.Response dto = fundingService.getItem(itemId);
        return ApiResponse.onSuccess(dto);
    }

    @GetMapping("/user") // 내 펀딩 목록 조회
    @Operation(summary = "내 참여한 펀딩목록 조회(item user list)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<?> itemDetails(@AuthenticationPrincipal UserDetails user) {
        List<ItemDto.ListResponse> item = fundingService.getUserItemList(user);
        return ApiResponse.onSuccess(item);
    }

    @PostMapping("/{itemId}") // 펀딩하기
    @Operation(summary = "구매 의사 등록(create funding)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    public ApiResponse<?> fund(@Valid FundingDto.SaveRequest dto, @AuthenticationPrincipal UserDetails user) {
        fundingService.saveFunding(dto, user);
        return ApiResponse.onSuccess("save successful");
    }

    @DeleteMapping("/item/{itemId}") // 펀딩 취소하기
    @Operation(summary = "구매 의사 취소(delete funding)")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
    })
    @Parameters({
            @Parameter(name = "itemId", description = "조회하려는 구매항목의 ID, path variable 입니다!"),
    })
    public ApiResponse<?> cancelFunding(@PathVariable("itemId") Long itemId, @AuthenticationPrincipal UserDetails user) {
        fundingService.deleteFunding(itemId, user);
        return ApiResponse.onSuccess("delete successful");
    }
}
