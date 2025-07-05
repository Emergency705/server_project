package emergency.server.converter;

import emergency.server.domain.Seller;
import emergency.server.dto.SellerDto;

public class SellerConverter {
    public static SellerDto.ListResponse toResponse(Seller seller) {
        return SellerDto.ListResponse.builder()
                .id(seller.getSellerId())
                .name(seller.getName())
                .build();
    }
}
