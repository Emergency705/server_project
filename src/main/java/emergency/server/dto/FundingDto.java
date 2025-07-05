package emergency.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class FundingDto {
    @Builder
    @Data
    public static class SaveRequest {
        @NotNull
        private Long itemId;
        @NotNull
        private Integer count;
    }
}
