package emergency.server.converter;

import emergency.server.domain.Item;
import emergency.server.dto.ItemDto;

public class ItemConverter {
    public static ItemDto.ListResponse toListResponse(Item item, Integer currentCount, Integer currentPrice) {
        return ItemDto.ListResponse.builder()
                .id(item.getItemId())
                .name(item.getName())
                .imageUrl(item.getImageUrl())
                .description(item.getDescription())
                .placeType(item.getPlaceType())
                .maxCount(item.getMaxCount())
                .currentCount(currentCount)
                .currentPrice(currentPrice)
                .build();
    }

    public static ItemDto.Response toResponse(Item item, Integer currentCount, Integer currentPrice) {
        return ItemDto.Response.builder()
                .id(item.getItemId())
                .name(item.getName())
                .description(item.getDescription())
                .imageUrl(item.getImageUrl())
                .startPrice(item.getStartPrice())
                .maxPrice(item.getMaxPrice())
                .placeType(item.getPlaceType())
                .closedDate(item.getClosedDate())
                .currentCount(currentCount)
                .currentPrice(currentPrice)
                .seller(SellerConverter.toResponse(item.getSeller()))
                .reviews(ReviewConverter.toListResponse(item.getReviews()))
                .build();
    }
}
