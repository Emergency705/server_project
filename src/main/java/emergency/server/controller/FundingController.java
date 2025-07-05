package emergency.server.controller;

import emergency.server.global.common.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funding")
@RequiredArgsConstructor
public class FundingController {

    @GetMapping("/") // item 목록 조회
    public ApiResponse<?> getItemList() {
        return null;
    }

    @GetMapping("/{itemId}") // 아이템 상세조회
    public ApiResponse<?> itemDetails(@PathVariable String itemId) {
        return null;
    }

    @PostMapping("/{itemId}") // 펀딩하기
    public ApiResponse<?> fund() {
        return null;
    }

    @PatchMapping("/{itemId}") // 펀딩 취소하기
    public ApiResponse<?> cancelFunding() {
        return null;
    }

    @GetMapping("/{itemId}/reviews") // 아이템에 해당하는 리뷰 조회
    public ApiResponse<?> reviews() {
        return null;
    }

}
