package emergency.server.dto;

import emergency.server.domain.enums.PlaceType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ItemDto {

    // 아이템 목록 조회 응답 DTO
    @Data
    @Builder
    public static class ListResponse {
        private Long id;
        private String name;
        private String imageUrl;
        private String description;
        private PlaceType placeType;
        private Integer maxCount;
        private Integer currentCount;
        private Integer currentPrice;
    }

    // 아이템 상세 조회 응답 DTO
    @Data
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String description;
        private String imageUrl;
        private Integer startPrice;
        private Integer maxPrice;
        private PlaceType placeType;
        private LocalDate closedDate;
        private Integer currentCount;
        private Integer currentPrice;
        private SellerDto.ListResponse seller;
        private List<ReviewDto.Response> reviews;
    }
}
