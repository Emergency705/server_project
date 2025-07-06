package emergency.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

public class ReviewDto {

    @Data
    @Builder
    public static class Response {
        private Long id;
        private String content;
        private String userName;
    }

    @Data
    @Builder
    public static class CreateRequest {
        @NotNull
        private Long itemId;
        @NotBlank
        private String content;
    }
}
