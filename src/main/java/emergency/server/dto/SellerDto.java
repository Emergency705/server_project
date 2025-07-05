package emergency.server.dto;

import lombok.Builder;
import lombok.Data;

public class SellerDto {
    @Builder
    @Data
    public static class ListResponse {
        private Long id;
        private String name;
    }
}
